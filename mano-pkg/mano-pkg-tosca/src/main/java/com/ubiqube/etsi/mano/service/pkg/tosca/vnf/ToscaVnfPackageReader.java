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
package com.ubiqube.etsi.mano.service.pkg.tosca.vnf;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.dao.mano.AdditionalArtifact;
import com.ubiqube.etsi.mano.dao.mano.L3Data;
import com.ubiqube.etsi.mano.dao.mano.ScalingAspect;
import com.ubiqube.etsi.mano.dao.mano.SecurityGroup;
import com.ubiqube.etsi.mano.dao.mano.SoftwareImage;
import com.ubiqube.etsi.mano.dao.mano.VlProtocolData;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfExtCp;
import com.ubiqube.etsi.mano.dao.mano.VnfLinkPort;
import com.ubiqube.etsi.mano.dao.mano.VnfStorage;
import com.ubiqube.etsi.mano.dao.mano.VnfVl;
import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainer;
import com.ubiqube.etsi.mano.dao.mano.pkg.OsContainerDeployableUnit;
import com.ubiqube.etsi.mano.dao.mano.pkg.VirtualCp;
import com.ubiqube.etsi.mano.dao.mano.vnfm.CnfImage;
import com.ubiqube.etsi.mano.dao.mano.vnfm.McIops;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.repository.BinaryRepository;
import com.ubiqube.etsi.mano.service.pkg.bean.AffinityRuleAdapater;
import com.ubiqube.etsi.mano.service.pkg.bean.ProviderData;
import com.ubiqube.etsi.mano.service.pkg.bean.SecurityGroupAdapter;
import com.ubiqube.etsi.mano.service.pkg.tosca.AbstractPackageReader;
import com.ubiqube.etsi.mano.service.pkg.vnf.VnfPackageReader;
import com.ubiqube.etsi.mano.tosca.ArtefactInformations;
import com.ubiqube.parser.tosca.Artifact;
import com.ubiqube.parser.tosca.ParseException;

import ma.glasnost.orika.MapperFactory;
import tosca.artifacts.nfv.HelmChart;
import tosca.artifacts.nfv.SwImage;
import tosca.datatypes.nfv.L3ProtocolData;
import tosca.datatypes.nfv.VirtualLinkProtocolData;
import tosca.nodes.nfv.Mciop;
import tosca.nodes.nfv.VNF;
import tosca.nodes.nfv.VduCp;
import tosca.nodes.nfv.VnfVirtualLink;
import tosca.nodes.nfv.vdu.Compute;
import tosca.nodes.nfv.vdu.VirtualBlockStorage;
import tosca.nodes.nfv.vdu.VirtualObjectStorage;
import tosca.policies.nfv.AffinityRule;
import tosca.policies.nfv.AntiAffinityRule;
import tosca.policies.nfv.InstantiationLevels;
import tosca.policies.nfv.ScalingAspects;
import tosca.policies.nfv.SecurityGroupRule;
import tosca.policies.nfv.VduInitialDelta;
import tosca.policies.nfv.VduInstantiationLevels;
import tosca.policies.nfv.VduScalingAspectDeltas;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class ToscaVnfPackageReader extends AbstractPackageReader implements VnfPackageReader {

	private static final String DESCRIPTOR_VERSION = "descriptorVersion";
	private static final String DESCRIPTOR_ID = "descriptorId";
	private static final String TOSCA_NAME = "toscaName";
	private static final String INTERNAL_NAME = "internalName";
	private static final Logger LOG = LoggerFactory.getLogger(ToscaVnfPackageReader.class);

	public ToscaVnfPackageReader(final InputStream data, final BinaryRepository repo, final UUID id) {
		super(data, repo, id);
	}

	@Override
	protected void additionalMapping(final MapperFactory mapperFactory) {
		mapperFactory.classMap(ProviderData.class, VNF.class)
				.field("vnfProvider", "provider")
				.field("vnfProductName", "productName")
				.field("vnfSoftwareVersion", "softwareVersion")
				.field("vnfdVersion", DESCRIPTOR_VERSION)
				.field(DESCRIPTOR_VERSION, DESCRIPTOR_VERSION)
				.field("vnfdId", DESCRIPTOR_ID)
				.field(DESCRIPTOR_ID, DESCRIPTOR_ID)
				.field("flavorId", "flavourId")
				.field("monitoringParameters{}", "monitoringParameters{value}")
				.field("monitoringParameters{name}", "monitoringParameters{key}")
				.field("scaleStatus{}", "scaleStatus{value}")
				.field("scaleStatus{name}", "scaleStatus{key}")
				.byDefault()
				.register();
		mapperFactory.classMap(ArtefactInformations.class, AdditionalArtifact.class)
				.field("path", "artifactPath")
				.field("checksum", "checksum.hash")
				.field("algorithm", "checksum.algorithm")
				.field("encrypted", "isEncrypted")
				.field("classifier", "artifactClassification")
				.byDefault()
				.customize(new AdditionalArtefactMapper())
				.register();
		mapperFactory.classMap(VirtualBlockStorage.class, VnfStorage.class)
				// .field("swImageData", "softwareImage")
				.field("artifacts{value}", "softwareImage")
				.field("artifacts{key}", "softwareImage.name")
				.field("virtualBlockStorageData.sizeOfStorage", "size")
				.field(INTERNAL_NAME, TOSCA_NAME)
				.field("", "myField:{|setType('BLOCK')}")
				.byDefault()
				.register();
		mapperFactory.classMap(VirtualObjectStorage.class, VnfStorage.class)
				.field("virtualObjectStorageData.maxSizeOfStorage", "size")
				.field(INTERNAL_NAME, TOSCA_NAME)
				.field("", "myField:{|setType('OBJECT')}")
				.byDefault()
				.register();
		mapperFactory.classMap(Compute.class, VnfCompute.class)
				.field("monitoringParameters{value}", "monitoringParameters{}")
				.field("monitoringParameters{key}", "monitoringParameters{name}")
				// .field("swImageData", "softwareImage")
				.field(INTERNAL_NAME, TOSCA_NAME)
				.field("virtualStorageReq", "storages")
				.field("virtualCompute.virtualCpu", "virtualCpu")
				.field("virtualCompute.virtualMemory", "virtualMemory")
				.field("virtualCompute.virtualLocalStorage[0].sizeOfStorage", "diskSize")
				.field("bootData.contentOrFileData.content", "cloudInit")
				.field("bootData.contentOrFileData.sourcePath", "sourcePath")
				.field("bootData.contentOrFileData.destinationPath", "destinationPath")
				.byDefault()
				.register();
		mapperFactory.classMap(VduCp.class, VnfLinkPort.class)
				.field("virtualBindingReq", "virtualBinding")
				.field("virtualLinkReq", "virtualLink")
				.field("order", "interfaceOrder")
				.field(INTERNAL_NAME, TOSCA_NAME)
				.byDefault()
				.register();
		mapperFactory.classMap(VnfVirtualLink.class, VnfVl.class)
				.field(INTERNAL_NAME, TOSCA_NAME)
				.field("vlProfile", "vlProfileEntity")
				.byDefault()
				.register();
		mapperFactory.classMap(VirtualLinkProtocolData.class, VlProtocolData.class)
				.field("l3ProtocolData.ipAllocationPools", "ipAllocationPools")
				.byDefault()
				.register();
		mapperFactory.classMap(L3ProtocolData.class, L3Data.class)
				.field("name", "l3Name")
				.byDefault()
				.register();
		mapperFactory.classMap(tosca.nodes.nfv.VnfExtCp.class, VnfExtCp.class)
				.field("externalVirtualLinkReq", "externalVirtualLink")
				.field("internalVirtualLinkReq", "internalVirtualLink")
				.field(INTERNAL_NAME, TOSCA_NAME)
				.byDefault()
				.register();
		mapperFactory.classMap(AffinityRule.class, com.ubiqube.etsi.mano.dao.mano.AffinityRule.class)
				.field(INTERNAL_NAME, TOSCA_NAME)
				.byDefault()
				.register();
		mapperFactory.classMap(AntiAffinityRule.class, com.ubiqube.etsi.mano.dao.mano.AffinityRule.class)
				.field(INTERNAL_NAME, TOSCA_NAME)
				.byDefault()
				.register();
		mapperFactory.classMap(SecurityGroupRule.class, SecurityGroup.class)
				.field(INTERNAL_NAME, TOSCA_NAME)
				.byDefault()
				.register();
		mapperFactory.classMap(VirtualBlockStorage.class, VnfStorage.class)
				.exclude("artifacts")
				.customize(new ArtefactMapper())
				.byDefault()
				.register();
		mapperFactory.classMap(SwImage.class, SoftwareImage.class)
				.field("file", "imagePath")
				.exclude("diskFormat")
				.exclude("containerFormat")
				.customize(new SwImageMapper())
				.byDefault()
				.register();
	}

	@Override
	public ProviderData getProviderPadata() {
		final List<ProviderData> vnfs = getListOf(VNF.class, ProviderData.class, new HashMap<>());
		if (vnfs.isEmpty()) {
			LOG.warn("No VNF node found in the package.");
			return new ProviderData();
		}
		return vnfs.get(0);
	}

	@Override
	public Set<AdditionalArtifact> getAdditionalArtefacts(final Map<String, String> parameters) {
		return getCsarFiles(AdditionalArtifact.class);
	}

	@Override
	public Set<VnfCompute> getVnfComputeNodes(final Map<String, String> parameters) {
		final Set<Compute> r = this.getSetOf(Compute.class, parameters);
		return r.stream().map(x -> {
			final VnfCompute o = getMapper().map(x, VnfCompute.class);
			Optional.ofNullable(x.getArtifacts()).ifPresent(y -> map(y, o));
			return o;
		}).collect(Collectors.toSet());
	}

	private void map(final Map<String, ?> y, final VnfCompute o) {
		if (y.isEmpty()) {
			return;
		}
		if (y.size() > 1) {
			throw new ParseException("Multiple artifacts match on compute node: " + o.getToscaName());
		}
		final Entry<String, ?> it = y.entrySet().iterator().next();
		final Object obj = it.getValue();
		if (!(obj instanceof final SwImage sw)) {
			throw new ParseException("Unknown artefact type: " + obj.getClass());
		}
		final SoftwareImage softwareImage = getMapper().map(sw, SoftwareImage.class);
		softwareImage.setDiskFormat(softwareImage.getDiskFormat());
		softwareImage.setName(it.getKey());
		o.setSoftwareImage(softwareImage);
	}

	@Override
	public Set<VnfStorage> getVnfStorages(final Map<String, String> parameters) {
		return getSetOf(VnfStorage.class, parameters, VirtualBlockStorage.class, VirtualObjectStorage.class);
	}

	@Override
	public Set<VnfVl> getVnfVirtualLinks(final Map<String, String> parameters) {
		return this.getSetOf(VnfVirtualLink.class, VnfVl.class, parameters);
	}

	@Override
	public Set<VnfLinkPort> getVnfVduCp(final Map<String, String> parameters) {
		return this.getSetOf(VduCp.class, VnfLinkPort.class, parameters);
	}

	@Override
	public Set<VnfExtCp> getVnfExtCp(final Map<String, String> parameters) {
		return this.getSetOf(tosca.nodes.nfv.VnfExtCp.class, VnfExtCp.class, parameters);
	}

	@Override
	public Set<ScalingAspect> getScalingAspects(final Map<String, String> parameters) {
		final List<ScalingAspects> list = getObjects(ScalingAspects.class, parameters);
		final Set<ScalingAspect> ret = new HashSet<>();
		for (final ScalingAspects scalingAspects : list) {
			final Map<String, tosca.datatypes.nfv.ScalingAspect> sa = scalingAspects.getAspects();
			final Set<ScalingAspect> tmp = sa.entrySet().stream().map(x -> {
				final ScalingAspect scaleRet = getMapper().map(x.getValue(), ScalingAspect.class);
				scaleRet.setName(x.getKey());
				return scaleRet;
			}).collect(Collectors.toSet());
			ret.addAll(tmp);
		}
		return ret;
	}

	@Override
	public List<com.ubiqube.etsi.mano.service.pkg.bean.InstantiationLevels> getInstatiationLevels(final Map<String, String> parameters) {
		return getListOf(InstantiationLevels.class, com.ubiqube.etsi.mano.service.pkg.bean.InstantiationLevels.class, parameters);
	}

	@Override
	public List<com.ubiqube.etsi.mano.service.pkg.bean.VduInstantiationLevels> getVduInstantiationLevels(final Map<String, String> parameters) {
		return getListOf(VduInstantiationLevels.class, com.ubiqube.etsi.mano.service.pkg.bean.VduInstantiationLevels.class, parameters);
	}

	@Override
	public List<com.ubiqube.etsi.mano.service.pkg.bean.VduInitialDelta> getVduInitialDelta(final Map<String, String> parameters) {
		return getListOf(VduInitialDelta.class, com.ubiqube.etsi.mano.service.pkg.bean.VduInitialDelta.class, parameters);
	}

	@Override
	public List<com.ubiqube.etsi.mano.service.pkg.bean.VduScalingAspectDeltas> getVduScalingAspectDeltas(final Map<String, String> parameters) {
		return getListOf(VduScalingAspectDeltas.class, com.ubiqube.etsi.mano.service.pkg.bean.VduScalingAspectDeltas.class, parameters);
	}

	@Override
	public Set<SecurityGroupAdapter> getSecurityGroups(final Map<String, String> userDefinedData) {
		final List<SecurityGroupRule> sgr = getObjects(SecurityGroupRule.class, userDefinedData);
		return sgr.stream().map(x -> new SecurityGroupAdapter(getMapper().map(x, SecurityGroup.class), x.getTargets())).collect(Collectors.toSet());
	}

	@Override
	public Set<AffinityRuleAdapater> getAffinityRules(final Map<String, String> userDefinedData) {
		final Set<AffinityRuleAdapater> af = getSetOf(AffinityRule.class, userDefinedData).stream()
				.map(x -> {
					final com.ubiqube.etsi.mano.dao.mano.AffinityRule afDao = getMapper().map(x, com.ubiqube.etsi.mano.dao.mano.AffinityRule.class);
					return AffinityRuleAdapater.of(afDao, x.getTargets());
				})
				.collect(Collectors.toSet());
		final Set<AffinityRuleAdapater> anf = getSetOf(AntiAffinityRule.class, userDefinedData).stream()
				.map(x -> {
					final com.ubiqube.etsi.mano.dao.mano.AffinityRule afDao = getMapper().map(x, com.ubiqube.etsi.mano.dao.mano.AffinityRule.class);
					afDao.setAnti(true);
					return AffinityRuleAdapater.of(afDao, x.getTargets());
				})
				.collect(Collectors.toSet());
		anf.forEach(x -> x.getAffinityRule().setAnti(true));
		af.addAll(anf);
		return af;
	}

	@Override
	public Set<OsContainer> getOsContainer(final Map<String, String> parameters) {
		return getSetOf(tosca.nodes.nfv.vdu.OsContainer.class, OsContainer.class, parameters);
	}

	@Override
	public Set<OsContainerDeployableUnit> getOsContainerDeployableUnit(final Map<String, String> parameters) {
		return getSetOf(tosca.nodes.nfv.vdu.OsContainerDeployableUnit.class, OsContainerDeployableUnit.class, parameters);
	}

	@Override
	public Set<VirtualCp> getVirtualCp(final Map<String, String> parameters) {
		return getSetOf(tosca.nodes.nfv.VirtualCp.class, VirtualCp.class, parameters);
	}

	@Override
	public Set<McIops> getMciops(final Map<String, String> userDefinedData) {
		final Set<Mciop> mciops = getSetOf(Mciop.class, userDefinedData);
		return mciops.stream().map(ToscaVnfPackageReader::map).collect(Collectors.toSet());
	}

	private static McIops map(final Mciop m) {
		final McIops ret = new McIops();
		ret.setAssociatedVdu(m.getAssociatedVduReq().stream().collect(Collectors.toSet()));
		ret.setToscaName(m.getInternalName());
		if (m.getArtifacts().size() != 1) {
			throw new GenericException("Size of artifact is incorrect, must be 1 but was " + m.getArtifacts().size());
		}
		final Entry<String, Artifact> arte = m.getArtifacts().entrySet().iterator().next();
		final Object obj = arte.getValue();
		if (!(obj instanceof final HelmChart av)) {
			throw new GenericException("Unknown class type " + obj);
		}
		final CnfImage image = new CnfImage();
		image.setToscaName(arte.getKey());
		image.setType(av.getType());
		image.setUrl(av.getFile());
		ret.setImage(image);
		return ret;
	}
}
