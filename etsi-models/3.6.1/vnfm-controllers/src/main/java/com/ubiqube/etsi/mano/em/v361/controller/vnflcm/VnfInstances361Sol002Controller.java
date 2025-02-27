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
package com.ubiqube.etsi.mano.em.v361.controller.vnflcm;

import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.linkTo;
import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.methodOn;
import static com.ubiqube.etsi.mano.vnfm.fc.Constants.getSafeUUID;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.dao.mano.CancelModeTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.v2.VnfBlueprint;
import com.ubiqube.etsi.mano.em.v361.model.vnflcm.ChangeCurrentVnfPkgRequest;
import com.ubiqube.etsi.mano.em.v361.model.vnflcm.ChangeExtVnfConnectivityRequest;
import com.ubiqube.etsi.mano.em.v361.model.vnflcm.ChangeVnfFlavourRequest;
import com.ubiqube.etsi.mano.em.v361.model.vnflcm.CreateVnfRequest;
import com.ubiqube.etsi.mano.em.v361.model.vnflcm.CreateVnfSnapshotRequest;
import com.ubiqube.etsi.mano.em.v361.model.vnflcm.HealVnfRequest;
import com.ubiqube.etsi.mano.em.v361.model.vnflcm.InstantiateVnfRequest;
import com.ubiqube.etsi.mano.em.v361.model.vnflcm.Link;
import com.ubiqube.etsi.mano.em.v361.model.vnflcm.OperateVnfRequest;
import com.ubiqube.etsi.mano.em.v361.model.vnflcm.RevertToVnfSnapshotRequest;
import com.ubiqube.etsi.mano.em.v361.model.vnflcm.ScaleVnfRequest;
import com.ubiqube.etsi.mano.em.v361.model.vnflcm.ScaleVnfToLevelRequest;
import com.ubiqube.etsi.mano.em.v361.model.vnflcm.TerminateVnfRequest;
import com.ubiqube.etsi.mano.em.v361.model.vnflcm.VnfInstance;
import com.ubiqube.etsi.mano.em.v361.model.vnflcm.VnfInstanceLinks;
import com.ubiqube.etsi.mano.vnfm.fc.vnflcm.VnfInstanceGenericFrontController;
import com.ubiqube.etsi.mano.vnfm.v361.controller.vnflcm.VnfInstances361Sol003Api;
import com.ubiqube.etsi.mano.vnfm.v361.controller.vnflcm.VnfLcmOpOccs361Sol003Api;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@RestController
public class VnfInstances361Sol002Controller implements VnfInstances361Sol002Api {
	private final VnfInstanceGenericFrontController frontController;

	public VnfInstances361Sol002Controller(final VnfInstanceGenericFrontController frontController) {
		super();
		this.frontController = frontController;
	}

	@Override
	public ResponseEntity<String> vnfInstancesGet(final MultiValueMap<String, String> requestParams, @Valid final String nextpageOpaqueMarker) {
		return frontController.search(requestParams, VnfInstance.class, nextpageOpaqueMarker, VnfInstances361Sol002Controller::makeLinks);
	}

	@Override
	public ResponseEntity<VnfInstance> vnfInstancesPost(@Valid final CreateVnfRequest body) {
		return frontController.create(body.getVnfdId(), body.getVnfInstanceName(), body.getVnfInstanceDescription(), VnfInstance.class,
				VnfInstances361Sol002Controller::makeLinks, "");
	}

	@Override
	public ResponseEntity<Void> vnfInstancesVnfInstanceIdChangeExtConnPost(final String vnfInstanceId, @Valid final ChangeExtVnfConnectivityRequest body) {
		return frontController.changeExtConn(getSafeUUID(vnfInstanceId), body, VnfInstances361Sol002Controller::getLcmLink);
	}

	@Override
	public ResponseEntity<Void> vnfInstancesVnfInstanceIdChangeFlavourPost(final String vnfInstanceId, @Valid final ChangeVnfFlavourRequest body) {
		return frontController.changeFlavour(getSafeUUID(vnfInstanceId), body, VnfInstances361Sol002Controller::getLcmLink);
	}

	@Override
	public ResponseEntity<Void> vnfInstancesVnfInstanceIdChangeVnfpkgPost(final String vnfInstanceId, @Valid final ChangeCurrentVnfPkgRequest body) {
		return frontController.changeVnfPkg(getSafeUUID(vnfInstanceId), body, VnfInstances361Sol002Controller::getLcmLink);
	}

	@Override
	public ResponseEntity<Void> vnfInstancesVnfInstanceIdCreateSnapshotPost(final String vnfInstanceId, @Valid final CreateVnfSnapshotRequest body) {
		return frontController.createSnapshot(getSafeUUID(vnfInstanceId), body, VnfInstances361Sol002Controller::getLcmLink);
	}

	@Override
	public ResponseEntity<Void> vnfInstancesVnfInstanceIdDelete(final String vnfInstanceId) {
		return frontController.deleteById(getSafeUUID(vnfInstanceId));
	}

	@Override
	public ResponseEntity<VnfInstance> vnfInstancesVnfInstanceIdGet(final String vnfInstanceId) {
		return frontController.findById(getSafeUUID(vnfInstanceId), VnfInstance.class, VnfInstances361Sol002Controller::makeLinks, "");
	}

	@Override
	public ResponseEntity<Void> vnfInstancesVnfInstanceIdHealPost(final String vnfInstanceId, @Valid final HealVnfRequest body) {
		return frontController.heal(getSafeUUID(vnfInstanceId), body.getCause(), new HashMap<>());
	}

	@Override
	public ResponseEntity<Void> vnfInstancesVnfInstanceIdInstantiatePost(final String vnfInstanceId, @Valid final InstantiateVnfRequest body) {
		return frontController.instantiate(getSafeUUID(vnfInstanceId), body, VnfInstances361Sol002Controller::getLcmLink);
	}

	@Override
	public ResponseEntity<Void> vnfInstancesVnfInstanceIdOperatePost(final String vnfInstanceId, @Valid final OperateVnfRequest body) {
		return frontController.operate(getSafeUUID(vnfInstanceId), body, VnfInstances361Sol002Controller::getLcmLink);
	}

	@Override
	public ResponseEntity<Void> vnfInstancesVnfInstanceIdPatch(final String vnfInstanceId, @Valid final String body, final String ifMatch) {
		return frontController.modify(getSafeUUID(vnfInstanceId), body, ifMatch, VnfInstances361Sol002Controller::getInstanceLink);
	}

	@Override
	public ResponseEntity<Void> vnfInstancesVnfInstanceIdRevertToSnapshotPost(final String vnfInstanceId, @Valid final RevertToVnfSnapshotRequest body) {
		return frontController.snapshot(getSafeUUID(vnfInstanceId), body);
	}

	@Override
	public ResponseEntity<Void> vnfInstancesVnfInstanceIdScalePost(final String vnfInstanceId, @Valid final ScaleVnfRequest body) {
		return frontController.scale(getSafeUUID(vnfInstanceId), body, VnfInstances361Sol002Controller::getLcmLink);
	}

	@Override
	public ResponseEntity<Void> vnfInstancesVnfInstanceIdScaleToLevelPost(final String vnfInstanceId, @Valid final ScaleVnfToLevelRequest body) {
		return frontController.scaleToLevel(getSafeUUID(vnfInstanceId), body, VnfInstances361Sol002Controller::getLcmLink);
	}

	@Override
	public ResponseEntity<Void> vnfInstancesVnfInstanceIdTerminatePost(final String vnfInstanceId, @Valid final TerminateVnfRequest body) {
		return frontController.terminate(getSafeUUID(vnfInstanceId), CancelModeTypeEnum.valueOf(body.getTerminationType().toString()), body.getGracefulTerminationTimeout(), VnfInstances361Sol002Controller::getLcmLink);
	}

	private static String getLcmLink(final VnfBlueprint vnfblueprint) {
		return linkTo(methodOn(VnfLcmOpOccs361Sol003Api.class).vnfLcmOpOccsVnfLcmOpOccIdGet(vnfblueprint.getId().toString())).withSelfRel().getHref();
	}

	private static String getInstanceLink(final com.ubiqube.etsi.mano.dao.mano.VnfInstance vnfInstance) {
		return linkTo(methodOn(VnfInstances361Sol003Api.class).vnfInstancesVnfInstanceIdGet(vnfInstance.getId().toString())).withSelfRel().getHref();
	}

	private static void makeLinks(final VnfInstance vnfInstance) {
		final String id = vnfInstance.getId();
		final String hrefScaleToLevel = linkTo(methodOn(VnfInstances361Sol002Api.class).vnfInstancesVnfInstanceIdScaleToLevelPost(id, null)).withSelfRel().getHref();
		final String hrefScale = linkTo(methodOn(VnfInstances361Sol002Api.class).vnfInstancesVnfInstanceIdScalePost(id, null)).withSelfRel().getHref();
		final String hrefOperate = linkTo(methodOn(VnfInstances361Sol002Api.class).vnfInstancesVnfInstanceIdOperatePost(id, null)).withSelfRel().getHref();
		final String hrefInstanciate = linkTo(methodOn(VnfInstances361Sol002Api.class).vnfInstancesVnfInstanceIdInstantiatePost(id, null)).withSelfRel().getHref();
		final String hrefIndicators = "";
		final String hrefHeal = linkTo(methodOn(VnfInstances361Sol002Api.class).vnfInstancesVnfInstanceIdHealPost(id, null)).withSelfRel().getHref();
		final String hrefChangeFlavor = linkTo(methodOn(VnfInstances361Sol002Api.class).vnfInstancesVnfInstanceIdChangeFlavourPost(id, null)).withSelfRel().getHref();
		final String hrefChangeExtConn = linkTo(methodOn(VnfInstances361Sol002Api.class).vnfInstancesVnfInstanceIdChangeExtConnPost(id, null)).withSelfRel().getHref();
		final String hrefSelf = linkTo(methodOn(VnfInstances361Sol002Api.class).vnfInstancesVnfInstanceIdGet(id)).withSelfRel().getHref();
		final String hrefTerminate = linkTo(methodOn(VnfInstances361Sol002Api.class).vnfInstancesVnfInstanceIdTerminatePost(id, null)).withSelfRel().getHref();
		final VnfInstanceLinks vnfInstanceLinks = new VnfInstanceLinks();
		final Link self = new Link();
		self.setHref(hrefSelf);
		vnfInstanceLinks.self(self);

		final Link changeExtConn = new Link();
		changeExtConn.setHref(hrefChangeExtConn);
		vnfInstanceLinks.setChangeExtConn(changeExtConn);

		final Link changeFlavour = new Link();
		changeFlavour.setHref(hrefChangeFlavor);
		vnfInstanceLinks.setChangeFlavour(changeFlavour);

		final Link heal = new Link();
		heal.setHref(hrefHeal);
		vnfInstanceLinks.setHeal(heal);

		final Link indicators = new Link();
		indicators.setHref(hrefIndicators);
		vnfInstanceLinks.setIndicators(indicators);

		final Link instantiate = new Link();
		instantiate.setHref(hrefInstanciate);
		vnfInstanceLinks.setInstantiate(instantiate);

		final Link operate = new Link();
		operate.setHref(hrefOperate);
		vnfInstanceLinks.setOperate(operate);

		final Link scale = new Link();
		scale.setHref(hrefScale);
		vnfInstanceLinks.setScale(scale);

		final Link terminate = new Link();
		terminate.setHref(hrefTerminate);
		vnfInstanceLinks.setTerminate(terminate);

		final Link scaleToLevel = new Link();
		scaleToLevel.setHref(hrefScaleToLevel);
		vnfInstanceLinks.setScaleToLevel(scaleToLevel);
		vnfInstance.setLinks(vnfInstanceLinks);
	}

}
