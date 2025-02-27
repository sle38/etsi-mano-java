package com.ubiqube.etsi.mano.vnfm.v361.controller.vnf;

import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.linkTo;
import static com.ubiqube.etsi.mano.uri.ManoWebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.controller.vnf.VnfSubscriptionSol003FrontController;
import com.ubiqube.etsi.mano.em.v361.model.vnflcm.Link;
import com.ubiqube.etsi.mano.nfvo.v361.model.vnf.PkgmSubscription;
import com.ubiqube.etsi.mano.nfvo.v361.model.vnf.PkgmSubscriptionLinks;
import com.ubiqube.etsi.mano.nfvo.v361.model.vnf.PkgmSubscriptionRequest;

/**
 *
 * @author olivier
 *
 */
@RestController
public class VnfSubscriptions361Sol003Controller implements VnfSubscriptions361Sol003Api {
	private final VnfSubscriptionSol003FrontController vnfSubscriptionSol03FrontController;

	public VnfSubscriptions361Sol003Controller(final VnfSubscriptionSol003FrontController vnfSubscriptionSol03FrontController) {
		this.vnfSubscriptionSol03FrontController = vnfSubscriptionSol03FrontController;
	}

	/**
	 * Query multiple subscriptions.
	 *
	 * The GET method queries the list of active subscriptions of the functional
	 * block that invokes the method. It can be used e.g. for resynchronization
	 * after error situations. This method shall follow the provisions specified in
	 * the Tables 9.4.7.8.2-1 and 9.4.8.3.2-2 for URI query parameters, request and
	 * response data structures, and response codes. ²
	 */
	@Override
	public ResponseEntity<List<PkgmSubscription>> subscriptionsGet(final String filter, final String nextpageOpaqueMarker) {
		return vnfSubscriptionSol03FrontController.search(filter, PkgmSubscription.class, VnfSubscriptions361Sol003Controller::makeLinks);
	}

	/**
	 * Subscribe to notifications related to on-boarding and/or changes of VNF
	 * packages.
	 *
	 * The POST method creates a new subscription. This method shall follow the
	 * provisions specified in the Tables 9.4.8.3.1-1 and 9.4.8.3.1-2 for URI query
	 * parameters, request and response data structures, and response codes.
	 * Creation of two subscription resources with the same callbackURI and the same
	 * filter can result in performance degradation and will provide duplicates of
	 * notifications to the OSS, and might make sense only in very rare use cases.
	 * Consequently, the NFVO may either allow creating a subscription resource if
	 * another subscription resource with the same filter and callbackUri already
	 * exists (in which case it shall return the \&quot;201 Created\&quot; response
	 * code), or may decide to not create a duplicate subscription resource (in
	 * which case it shall return a \&quot;303 See Other\&quot; response code
	 * referencing the existing subscription resource with the same filter and
	 * callbackUri).
	 *
	 */
	@Override
	public ResponseEntity<PkgmSubscription> subscriptionsPost(final PkgmSubscriptionRequest subscriptionsPostQuery) {
		return vnfSubscriptionSol03FrontController.create(subscriptionsPostQuery, PkgmSubscription.class, VnfSubscriptions361Sol003Controller::makeLinks);
	}

	/**
	 * Terminate a subscription.
	 *
	 * The DELETE method terminates an individual subscription.
	 *
	 */
	@Override
	public ResponseEntity<Void> subscriptionsSubscriptionIdDelete(final String subscriptionId) {
		return vnfSubscriptionSol03FrontController.delete(subscriptionId);
	}

	/**
	 * Read an individual subscription resource.
	 *
	 * Query Subscription Information The GET method reads an individual
	 * subscription.
	 *
	 */
	@Override
	public ResponseEntity<PkgmSubscription> subscriptionsSubscriptionIdGet(final String subscriptionId) {
		return vnfSubscriptionSol03FrontController.findById(subscriptionId, PkgmSubscription.class, VnfSubscriptions361Sol003Controller::makeLinks);
	}

	public static void makeLinks(final PkgmSubscription pkgmSubscription) {
		final PkgmSubscriptionLinks subscriptionsPkgmSubscriptionLinks = new PkgmSubscriptionLinks();
		final Link self = new Link();
		self.setHref(linkTo(methodOn(VnfSubscriptions361Sol003Api.class).subscriptionsSubscriptionIdGet(pkgmSubscription.getId())).withSelfRel().getHref());
		subscriptionsPkgmSubscriptionLinks.setSelf(self);
		pkgmSubscription.setLinks(subscriptionsPkgmSubscriptionLinks);
	}

}
