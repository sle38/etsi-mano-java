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
package com.ubiqube.etsi.mano.nfvo.v361.controller.vnf;

import static com.ubiqube.etsi.mano.nfvo.fc.controller.NfvoConstants.getSafeUUID;
import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.linkTo;
import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.methodOn;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ubiqube.etsi.mano.controller.vnf.VnfPackageFrontController;
import com.ubiqube.etsi.mano.em.v361.model.vnflcm.Link;
import com.ubiqube.etsi.mano.nfvo.v361.model.vnf.CreateVnfPkgInfoRequest;
import com.ubiqube.etsi.mano.nfvo.v361.model.vnf.ExternalArtifactsAccessConfig;
import com.ubiqube.etsi.mano.nfvo.v361.model.vnf.UploadVnfPkgFromUriRequest;
import com.ubiqube.etsi.mano.nfvo.v361.model.vnf.VnfPkgInfo;
import com.ubiqube.etsi.mano.nfvo.v361.model.vnf.VnfPkgInfoLinks;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@RestController
public class VnfPackages361Sol005Controller implements VnfPackages361Sol005Api {
	private final VnfPackageFrontController frontController;

	public VnfPackages361Sol005Controller(final VnfPackageFrontController frontController) {
		super();
		this.frontController = frontController;
	}

	@Override
	public ResponseEntity<String> vnfPackagesGet(final MultiValueMap<String, String> requestParams, @Valid final String nextpageOpaqueMarker) {
		return frontController.search(requestParams, VnfPkgInfo.class, VnfPackages361Sol005Controller::makeLinks);
	}

	@Override
	public ResponseEntity<VnfPkgInfo> vnfPackagesPost(@Valid final CreateVnfPkgInfoRequest body) {
		return frontController.create(body.getUserDefinedData(), VnfPkgInfo.class, VnfPackages361Sol005Controller::makeLinks, null);
	}

	@Override
	public ResponseEntity<Resource> vnfPackagesVnfPkgIdArtifactsArtifactPathGet(final String vnfPkgId, final HttpServletRequest requestParams, @Valid final String includeSignatures) {
		return frontController.getArtifactPath(requestParams, getSafeUUID(vnfPkgId), includeSignatures);
	}

	@Override
	public ResponseEntity<Resource> vnfPackagesVnfPkgIdArtifactsGet(final String vnfPkgId, final String includeSignatures, final String excludeAllManoArtifacts, final String excludeAllNonManoArtifacts, final String selectNonManoArtifactSets, final String includeExternalArtifacts) {
		return frontController.searchArtifact(getSafeUUID(vnfPkgId), includeSignatures, excludeAllManoArtifacts, excludeAllNonManoArtifacts, selectNonManoArtifactSets);
	}

	@Override
	public ResponseEntity<Void> vnfPackagesVnfPkgIdDelete(final String vnfPkgId) {
		return frontController.deleteById(getSafeUUID(vnfPkgId));
	}

	@Override
	public ResponseEntity<ExternalArtifactsAccessConfig> vnfPackagesVnfPkgIdExtArtifactsAccessGet(final String vnfPkgId) {
		return frontController.getExternalArtifacts(getSafeUUID(vnfPkgId));
	}

	@Override
	public ResponseEntity<ExternalArtifactsAccessConfig> vnfPackagesVnfPkgIdExtArtifactsAccessPut(@Valid final ExternalArtifactsAccessConfig body, final String vnfPkgId) {
		return frontController.putExternalArtifact(body, getSafeUUID(vnfPkgId));
	}

	@Override
	public ResponseEntity<VnfPkgInfo> vnfPackagesVnfPkgIdGet(final String vnfPkgId) {
		return frontController.findById(getSafeUUID(vnfPkgId), VnfPkgInfo.class, VnfPackages361Sol005Controller::makeLinks);
	}

	@Override
	public ResponseEntity<Resource> vnfPackagesVnfPkgIdManifestGet(final String vnfPkgId, @Valid final String includeSignatures) {
		return frontController.getManifest(getSafeUUID(vnfPkgId), includeSignatures);
	}

	@Override
	public ResponseEntity<Resource> vnfPackagesVnfPkgIdPackageContentGet(final String vnfPkgId) {
		return frontController.getContent(getSafeUUID(vnfPkgId));
	}

	@Override
	public ResponseEntity<Void> vnfPackagesVnfPkgIdPackageContentPut(@Valid final MultipartFile file, final String accept, final String vnfPkgId) {
		return frontController.putContent(getSafeUUID(vnfPkgId), accept, file);
	}

	@Override
	public ResponseEntity<Void> vnfPackagesVnfPkgIdPackageContentUploadFromUriPost(@Valid final UploadVnfPkgFromUriRequest body, final String vnfPkgId, final String accept) {
		return frontController.uploadFromUri(body, getSafeUUID(vnfPkgId), accept);
	}

	@Override
	public ResponseEntity<VnfPkgInfo> vnfPackagesVnfPkgIdPatch(@Valid final String body, final String vnfPkgId, final String ifMatch) {
		return frontController.modify(body, getSafeUUID(vnfPkgId), ifMatch, VnfPkgInfo.class, VnfPackages361Sol005Controller::makeLinks);
	}

	@Override
	public ResponseEntity<Resource> vnfPackagesVnfPkgIdVnfdGet(final String vnfPkgId, final String accept, @Valid final String includeSignatures) {
		return frontController.getVfnd(getSafeUUID(vnfPkgId), null, includeSignatures);
	}

	private static void makeLinks(final VnfPkgInfo vnfPackage) {
		final String vnfPkgId = vnfPackage.getId();
		final VnfPkgInfoLinks links = new VnfPkgInfoLinks();

		final Link self = new Link();
		self.setHref(linkTo(methodOn(VnfPackages361Sol005Api.class).vnfPackagesVnfPkgIdGet(vnfPkgId)).withSelfRel().getHref());
		links.self(self);

		final Link vnfd = new Link();
		vnfd.setHref(linkTo(methodOn(VnfPackages361Sol005Api.class).vnfPackagesVnfPkgIdVnfdGet(vnfPkgId, null, null)).withSelfRel().getHref());
		links.setVnfd(vnfd);

		final Link packageContent = new Link();
		packageContent.setHref(linkTo(methodOn(VnfPackages361Sol005Api.class).vnfPackagesVnfPkgIdPackageContentGet(vnfPkgId)).withSelfRel().getHref());
		links.setPackageContent(packageContent);
		vnfPackage.setLinks(links);
	}

}
