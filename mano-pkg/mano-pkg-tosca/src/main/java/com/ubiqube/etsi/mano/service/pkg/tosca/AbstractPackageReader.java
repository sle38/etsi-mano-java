/**
 *     Copyright (C) 2019-2020 Ubiqube.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.service.pkg.tosca;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.Constants;
import com.ubiqube.etsi.mano.repository.BinaryRepository;
import com.ubiqube.etsi.mano.service.pkg.PkgUtils;
import com.ubiqube.etsi.mano.sol004.CsarModeEnum;
import com.ubiqube.etsi.mano.tosca.ArtefactInformations;
import com.ubiqube.parser.tosca.Import;
import com.ubiqube.parser.tosca.Imports;
import com.ubiqube.parser.tosca.ParseException;
import com.ubiqube.parser.tosca.ToscaContext;
import com.ubiqube.parser.tosca.ToscaParser;
import com.ubiqube.parser.tosca.api.ToscaApi;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public abstract class AbstractPackageReader implements Closeable {

	private static final String FOUND_NODE_IN_TOSCA_MODEL = "Found {} {} node in TOSCA model";

	private static final Logger LOG = LoggerFactory.getLogger(AbstractPackageReader.class);

	private final ToscaContext root;
	private final MapperFacade mapper;

	private final ToscaParser toscaParser;

	private final File tempFile;

	private final BinaryRepository repo;

	protected AbstractPackageReader(final InputStream data, final BinaryRepository repo, final UUID id) {
		this.repo = repo;
		tempFile = PkgUtils.fetchData(data);
		toscaParser = new ToscaParser(tempFile);
		final CsarModeEnum mode = toscaParser.getMode();
		if (mode == CsarModeEnum.DOUBLE_ZIP) {
			unpackAndResend(id);
		}
		root = toscaParser.getContext();
		final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		additionalMapping(mapperFactory);
		final ConverterFactory converterFactory = mapperFactory.getConverterFactory();
		converterFactory.registerConverter(new SizeConverter());
		converterFactory.registerConverter(new TimeConverter());
		converterFactory.registerConverter(new FrequencyConverter());
		mapper = mapperFactory.getMapperFacade();
	}

	private void unpackAndResend(@NotNull final UUID id) {
		try (final InputStream is = toscaParser.getCsarInputStream()) {
			repo.storeBinary(id, Constants.REPOSITORY_FILENAME_VNFD, is);
		} catch (final IOException e) {
			throw new ParseException(e);
		}
	}

	protected abstract void additionalMapping(MapperFactory mapperFactory);

	@SuppressWarnings("null")
	@Nonnull
	protected <T, U> Set<U> getSetOf(final Class<T> toscaClass, final Class<U> to, final Map<String, String> parameters) {
		final List<T> list = ToscaApi.getObjects(root, parameters, toscaClass);
		LOG.debug(FOUND_NODE_IN_TOSCA_MODEL, list.size(), toscaClass.getSimpleName());
		return list.stream()
				.map(x -> mapper.map(x, to))
				.collect(Collectors.toSet());
	}

	@Nonnull
	protected <T> Set<T> getSetOf(final Class<T> toscaClass, final Map<String, String> parameters) {
		final List<T> list = ToscaApi.getObjects(root, parameters, toscaClass);
		LOG.debug(FOUND_NODE_IN_TOSCA_MODEL, list.size(), toscaClass.getSimpleName());
		return list.stream()
				.collect(Collectors.toSet());
	}

	@SuppressWarnings("null")
	@Nonnull
	protected <T, U> List<U> getListOf(final Class<T> toscaClass, final Class<U> to, final Map<String, String> parameters) {
		final List<T> obj = ToscaApi.getObjects(root, parameters, toscaClass);
		LOG.debug(FOUND_NODE_IN_TOSCA_MODEL, obj.size(), toscaClass.getSimpleName());
		return mapper.mapAsList(obj, to);
	}

	@SuppressWarnings("null")
	@Nonnull
	protected <U> List<U> getObjects(final Class<U> toscaClass, final Map<String, String> parameters) {
		final List<U> obj = ToscaApi.getObjects(root, parameters, toscaClass);
		LOG.debug(FOUND_NODE_IN_TOSCA_MODEL, obj.size(), toscaClass.getSimpleName());
		return obj;
	}

	@Nonnull
	protected List<ArtefactInformations> getCsarFiles() {
		return toscaParser.getFiles();
	}

	@Nonnull
	protected <U> Set<U> getCsarFiles(final Class<U> dest) {
		return toscaParser.getFiles().stream()
				.map(x -> mapper.map(x, dest))
				.collect(Collectors.toSet());
	}

	@Nonnull
	protected <U> Set<U> getSetOf(final Class<U> to, final Map<String, String> parameters, final Class<?>... toscaClass) {
		final Set<U> ret = new LinkedHashSet<>();
		for (final Class<?> class1 : toscaClass) {
			final Set<U> set = getSetOf(class1, to, parameters);
			ret.addAll(set);
		}
		return ret;
	}

	protected MapperFacade getMapper() {
		return mapper;
	}

	@Override
	public void close() throws IOException {
		Files.delete(tempFile.toPath());
	}

	public final List<String> getImports() {
		final Imports imps = this.root.getImports();
		final String entry = this.toscaParser.getEntryFileName();
		final List<String> imports = imps.entrySet().stream()
				.map(Entry::getValue)
				.map(Import::getResolved)
				.toList();
		final ArrayList<String> ret = new ArrayList<>(imports);
		ret.add(entry);
		return ret;
	}

	public final String getManifestContent() {
		return this.toscaParser.getManifestContent();
	}

	public byte[] getFileContent(final String fileName) {
		return toscaParser.getFileContent(fileName);
	}

	public InputStream getFileInputStream(final String path) {
		return toscaParser.getFileInputStream(path);
	}
}
