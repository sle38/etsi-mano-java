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

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.em.v361.model.vnflcm.LccnSubscription;
import com.ubiqube.etsi.mano.em.v361.model.vnflcm.LccnSubscriptionLinks;
import com.ubiqube.etsi.mano.em.v361.model.vnflcm.LccnSubscriptionRequest;
import com.ubiqube.etsi.mano.em.v361.model.vnflcm.Link;
import com.ubiqube.etsi.mano.vnfm.fc.vnflcm.VnfLcmSubscriptionFrontController;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@RestController
public class VnfLcmSubscriptions361Sol002Controller implements VnfLcmSubscriptions361Sol002Api {
	private final VnfLcmSubscriptionFrontController frontController;

	public VnfLcmSubscriptions361Sol002Controller(final VnfLcmSubscriptionFrontController frontController) {
		super();
		this.frontController = frontController;
	}

	@Override
	public ResponseEntity<List<LccnSubscription>> subscriptionsGet(final MultiValueMap<String, String> requestParams, @Valid final String nextpageOpaqueMarker) {
		return frontController.search(requestParams, nextpageOpaqueMarker, LccnSubscription.class, VnfLcmSubscriptions361Sol002Controller::makeLinks);
	}

	@Override
	public ResponseEntity<LccnSubscription> subscriptionsPost(@Valid final LccnSubscriptionRequest body) {
		return frontController.create(body, LccnSubscription.class, VnfLcmSubscriptions361Sol002Controller::makeLinks, VnfLcmSubscriptions361Sol002Controller::getSelfLink);
	}

	@Override
	public ResponseEntity<Void> subscriptionsSubscriptionIdDelete(final String subscriptionId) {
		return frontController.deleteById(subscriptionId);
	}

	@Override
	public ResponseEntity<LccnSubscription> subscriptionsSubscriptionIdGet(final String subscriptionId) {
		return frontController.findById(subscriptionId, LccnSubscription.class, VnfLcmSubscriptions361Sol002Controller::makeLinks);
	}

	private static void makeLinks(final LccnSubscription subscription) {
		final LccnSubscriptionLinks links = new LccnSubscriptionLinks();
		final Link link = new Link();
		link.setHref(linkTo(methodOn(VnfLcmSubscriptions361Sol002Api.class).subscriptionsSubscriptionIdGet(subscription.getId())).withSelfRel().getHref());
		links.setSelf(link);
		subscription.setLinks(links);
	}

	private static String getSelfLink(final LccnSubscription subscription) {
		return linkTo(methodOn(VnfLcmSubscriptions361Sol002Api.class).subscriptionsSubscriptionIdGet(subscription.getId())).withSelfRel().getHref();
	}

}
