package com.ubiqube.etsi.mano.em.v271.controller.vnflcm;

import static com.ubiqube.etsi.mano.Constants.getSafeUUID;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;

import com.ubiqube.etsi.mano.controller.nslcm.VnfLcmSubscriptionFrontController;
import com.ubiqube.etsi.mano.em.v271.model.vnflcm.LccnSubscription;
import com.ubiqube.etsi.mano.em.v271.model.vnflcm.LccnSubscriptionLinks;
import com.ubiqube.etsi.mano.em.v271.model.vnflcm.LccnSubscriptionRequest;
import com.ubiqube.etsi.mano.em.v271.model.vnflcm.Link;

@Controller
public class VnfLcmSubscriptions271Sol002Controller implements VnfLcmSubscriptions271Sol0à2Api {
	private final VnfLcmSubscriptionFrontController frontController;

	public VnfLcmSubscriptions271Sol002Controller(final VnfLcmSubscriptionFrontController frontController) {
		super();
		this.frontController = frontController;
	}

	@Override
	public ResponseEntity<List<LccnSubscription>> subscriptionsGet(final MultiValueMap<String, String> requestParams, @Valid final String nextpageOpaqueMarker) {
		return frontController.search(requestParams, nextpageOpaqueMarker, LccnSubscription.class, VnfLcmSubscriptions271Sol002Controller::makeLinks);
	}

	@Override
	public ResponseEntity<LccnSubscription> subscriptionsPost(@Valid final LccnSubscriptionRequest body) {
		return frontController.create(body, LccnSubscription.class, VnfLcmSubscriptions271Sol002Controller::makeLinks, VnfLcmSubscriptions271Sol002Controller::getSelfLink);
	}

	@Override
	public ResponseEntity<Void> subscriptionsSubscriptionIdDelete(final String subscriptionId) {
		return frontController.deleteById(getSafeUUID(subscriptionId));
	}

	@Override
	public ResponseEntity<LccnSubscription> subscriptionsSubscriptionIdGet(final String subscriptionId) {
		return frontController.findById(getSafeUUID(subscriptionId), LccnSubscription.class, VnfLcmSubscriptions271Sol002Controller::makeLinks);
	}

	private static void makeLinks(final LccnSubscription subscription) {
		final LccnSubscriptionLinks links = new LccnSubscriptionLinks();
		final Link link = new Link();
		link.setHref(linkTo(methodOn(VnfLcmSubscriptions271Sol0à2Api.class).subscriptionsSubscriptionIdGet(subscription.getId())).withSelfRel().getHref());
		links.setSelf(link);
		subscription.setLinks(links);
	}

	private static String getSelfLink(final LccnSubscription subscription) {
		return linkTo(methodOn(VnfLcmSubscriptions271Sol0à2Api.class).subscriptionsSubscriptionIdGet(subscription.getId())).withSelfRel().getHref();
	}
}
