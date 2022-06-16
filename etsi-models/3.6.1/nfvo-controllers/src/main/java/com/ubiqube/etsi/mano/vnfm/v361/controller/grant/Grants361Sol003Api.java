/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.24).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.ubiqube.etsi.mano.vnfm.v361.controller.grant;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubiqube.etsi.mano.vnfm.v361.model.grant.Grant;
import com.ubiqube.etsi.mano.vnfm.v361.model.grant.GrantRequest;
import com.ubiqube.etsi.mano.vnfm.v361.model.grant.ProblemDetails2;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface Grants361Sol003Api {

	Logger log = LoggerFactory.getLogger(Grants361Sol003Api.class);

	default Optional<ObjectMapper> getObjectMapper() {
		return Optional.empty();
	}

	default Optional<HttpServletRequest> getRequest() {
		return Optional.empty();
	}

	default Optional<String> getAcceptHeader() {
		return getRequest().map(r -> r.getHeader("Accept"));
	}

	@Operation(summary = "", description = "The GET method reads a grant. See clause 9.4.3.3.2. ", tags = {})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "200 OK  Shall be returned when the grant has been read successfully. A representation of the \"Individual grant\" resource shall be returned in the response body. ", content = @Content(schema = @Schema(implementation = Grant.class))),

			@ApiResponse(responseCode = "202", description = "202 ACCEPTED  Shall be returned when the process of creating the grant is ongoing, no grant is available yet. The response body shall be empty. "),

			@ApiResponse(responseCode = "400", description = "400 BAD REQUEST 400 code can be returned in the following specified cases, the specific cause has to be proper specified in the \"ProblemDetails\" structure to be returned. If the request is malformed or syntactically incorrect (e.g. if the request URI contains incorrect query parameters or the payload body contains a syntactically incorrect data structure), the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided, and should include in the \"detail\" attribute more information about the source of the problem. If the response to a GET request which queries a container resource would be so big that the performance of the API producer is adversely affected, and the API producer does not support paging for the affected resource, it shall respond with this response code. The \"ProblemDetails\" structure shall be provided, and should include in the \"detail\" attribute more information about the source of the problem. If there is an application error related to the client's input that cannot be easily mapped to any other HTTP response code (\"catch all error\"), the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided, and shall include in the \"detail\" attribute more information about the source of the problem. If the request contains a malformed access token, the API producer should respond with this response. The details of the error shall be returned in the WWW Authenticate HTTP header, as defined in IETF RFC 6750 and IETF RFC 7235. The ProblemDetails structure may be provided. The use of this HTTP error response code described above is applicable to the use of the OAuth 2.0 for the authorization of API requests and notifications, as defined in clauses 4.5.3.3 and 4.5.3.4. ", content = @Content(schema = @Schema(implementation = ProblemDetails2.class))),

			@ApiResponse(responseCode = "401", description = "401 UNAUTHORIZED If the request contains no access token even though one is required, or if the request contains an authorization token that is invalid (e.g. expired or revoked), the API producer should respond with this response. The details of the error shall be returned in the WWW-Authenticate HTTP header, as defined in IETF RFC 6750 and IETF RFC 7235. The ProblemDetails structure may be provided. ", content = @Content(schema = @Schema(implementation = ProblemDetails2.class))),

			@ApiResponse(responseCode = "403", description = "403 FORBIDDEN  Shall be returned upon the following error: The grant has been rejected. A ProblemDetails structure shall be included in the response to provide more details about the rejection in the \"details\" attribute. ", content = @Content(schema = @Schema(implementation = ProblemDetails2.class))),

			@ApiResponse(responseCode = "404", description = "404 NOT FOUND If the API producer did not find a current representation for the resource addressed by the URI passed in the request or is not willing to disclose that one exists, it shall respond with this response code. The \"ProblemDetails\" structure may be provided, including in the \"detail\" attribute information about the source of the problem, e.g. a wrong resource URI variable. This response code is not appropriate in case the resource addressed by the URI is a container resource which is designed to contain child resources, but does not contain any child resource at the time the request is received. For a GET request to an existing empty container resource, a typical response contains a 200 OK response code and a payload body with an empty array. ", content = @Content(schema = @Schema(implementation = ProblemDetails2.class))),

			@ApiResponse(responseCode = "405", description = "405 METHOD NOT ALLOWED If a particular HTTP method is not supported for a particular resource, the API producer shall respond with this response code. The \"ProblemDetails\" structure may be omitted. ", content = @Content(schema = @Schema(implementation = ProblemDetails2.class))),

			@ApiResponse(responseCode = "406", description = "406 NOT ACCEPTABLE If the \"Accept\" HTTP header does not contain at least one name of a content type that is acceptable to the API producer, the API producer shall respond with this response code. The \"ProblemDetails\" structure may be omitted. ", content = @Content(schema = @Schema(implementation = ProblemDetails2.class))),

			@ApiResponse(responseCode = "422", description = "422 UNPROCESSABLE ENTITY If the payload body of a request contains syntactically correct data (e.g. well-formed JSON) but the data cannot be processed (e.g. because it fails validation against a schema), the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided, and should include in the \"detail\" attribute more information about the source of the problem. This error response code is only applicable for methods that have a request body. ", content = @Content(schema = @Schema(implementation = ProblemDetails2.class))),

			@ApiResponse(responseCode = "500", description = "500 INTERNAL SERVER ERROR If there is an application error not related to the client's input that cannot be easily mapped to any other HTTP response code (\"catch all error\"), the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided, and shall include in the \"detail\" attribute more information about the source of the problem. ", content = @Content(schema = @Schema(implementation = ProblemDetails2.class))),

			@ApiResponse(responseCode = "503", description = "503 SERVICE UNAVAILABLE If the API producer encounters an internal overload situation of itself or of a system it relies on, it should respond with this response code, following the provisions in IETF RFC 7231 for the use of the \"Retry-After\" HTTP header and for the alternative to refuse the connection. The \"ProblemDetails\" structure may be omitted. ", content = @Content(schema = @Schema(implementation = ProblemDetails2.class))),

			@ApiResponse(responseCode = "504", description = "504 GATEWAY TIMEOUT If the API producer encounters a timeout while waiting for a response from an upstream server (i.e. a server that the API producer communicates with when fulfilling a request), it should respond with this response code. ", content = @Content(schema = @Schema(implementation = ProblemDetails2.class))) })
	@RequestMapping(value = "/grants/{grantId}", produces = { "application/json" }, method = RequestMethod.GET)
	default ResponseEntity<Grant> grantsGrantIdGet(@Parameter(in = ParameterIn.PATH, description = "Identifier of the grant. This identifier can be retrieved from the resource referenced by the \"Location\" HTTP header in the response to a POST request granting a new VNF lifecycle operation. It can also be retrieved from the \"id\" attribute in the payload body of that response. ", required = true, schema = @Schema()) @PathVariable("grantId") final String grantId, @Parameter(in = ParameterIn.HEADER, description = "Content-Types that are acceptable for the response. Reference: IETF RFC 7231. ", required = true, schema = @Schema()) @RequestHeader(value = "Accept", required = true) final String accept, @Parameter(in = ParameterIn.HEADER, description = "Version of the API requested to use when responding to this request. ", required = true, schema = @Schema()) @RequestHeader(value = "Version", required = true) final String version, @Parameter(in = ParameterIn.HEADER, description = "The authorization token for the request. Reference: IETF RFC 7235. ", schema = @Schema()) @RequestHeader(value = "Authorization", required = false) final String authorization) {
		if (getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
			if (getAcceptHeader().get().contains("application/json")) {
				try {
					return new ResponseEntity<>(getObjectMapper().get().readValue(
							"{\n  \"extManagedVirtualLinks\" : [ {\n    \"vnfLinkPort\" : [ { }, { } ]\n  }, {\n    \"vnfLinkPort\" : [ { }, { } ]\n  } ],\n  \"vimConnectionInfo\" : {\n    \"key\" : {\n      \"vimType\" : \"vimType\",\n      \"interfaceInfo\" : { }\n    }\n  },\n  \"tempResources\" : [ null, null ],\n  \"zoneGroups\" : [ {\n    \"zoneId\" : [ null, null ]\n  }, {\n    \"zoneId\" : [ null, null ]\n  } ],\n  \"_links\" : {\n    \"self\" : {\n      \"href\" : \"href\"\n    }\n  },\n  \"extVirtualLinks\" : [ {\n    \"extLinkPorts\" : [ {\n      \"resourceHandle\" : {\n        \"vimLevelResourceType\" : \"vimLevelResourceType\"\n      }\n    }, {\n      \"resourceHandle\" : {\n        \"vimLevelResourceType\" : \"vimLevelResourceType\"\n      }\n    } ],\n    \"extCps\" : [ {\n      \"cpConfig\" : {\n        \"key\" : {\n          \"cpProtocolData\" : [ {\n            \"ipOverEthernet\" : {\n              \"macAddress\" : \"macAddress\",\n              \"segmentationType\" : \"VLAN\",\n              \"ipAddresses\" : [ {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              }, {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              } ],\n              \"segmentationId\" : \"segmentationId\"\n            },\n            \"layerProtocol\" : \"IP_OVER_ETHERNET\"\n          }, {\n            \"ipOverEthernet\" : {\n              \"macAddress\" : \"macAddress\",\n              \"segmentationType\" : \"VLAN\",\n              \"ipAddresses\" : [ {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              }, {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              } ],\n              \"segmentationId\" : \"segmentationId\"\n            },\n            \"layerProtocol\" : \"IP_OVER_ETHERNET\"\n          } ],\n          \"parentCpConfigId\" : \"parentCpConfigId\",\n          \"createExtLinkPort\" : true\n        }\n      }\n    }, {\n      \"cpConfig\" : {\n        \"key\" : {\n          \"cpProtocolData\" : [ {\n            \"ipOverEthernet\" : {\n              \"macAddress\" : \"macAddress\",\n              \"segmentationType\" : \"VLAN\",\n              \"ipAddresses\" : [ {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              }, {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              } ],\n              \"segmentationId\" : \"segmentationId\"\n            },\n            \"layerProtocol\" : \"IP_OVER_ETHERNET\"\n          }, {\n            \"ipOverEthernet\" : {\n              \"macAddress\" : \"macAddress\",\n              \"segmentationType\" : \"VLAN\",\n              \"ipAddresses\" : [ {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              }, {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              } ],\n              \"segmentationId\" : \"segmentationId\"\n            },\n            \"layerProtocol\" : \"IP_OVER_ETHERNET\"\n          } ],\n          \"parentCpConfigId\" : \"parentCpConfigId\",\n          \"createExtLinkPort\" : true\n        }\n      }\n    } ]\n  }, {\n    \"extLinkPorts\" : [ {\n      \"resourceHandle\" : {\n        \"vimLevelResourceType\" : \"vimLevelResourceType\"\n      }\n    }, {\n      \"resourceHandle\" : {\n        \"vimLevelResourceType\" : \"vimLevelResourceType\"\n      }\n    } ],\n    \"extCps\" : [ {\n      \"cpConfig\" : {\n        \"key\" : {\n          \"cpProtocolData\" : [ {\n            \"ipOverEthernet\" : {\n              \"macAddress\" : \"macAddress\",\n              \"segmentationType\" : \"VLAN\",\n              \"ipAddresses\" : [ {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              }, {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              } ],\n              \"segmentationId\" : \"segmentationId\"\n            },\n            \"layerProtocol\" : \"IP_OVER_ETHERNET\"\n          }, {\n            \"ipOverEthernet\" : {\n              \"macAddress\" : \"macAddress\",\n              \"segmentationType\" : \"VLAN\",\n              \"ipAddresses\" : [ {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              }, {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              } ],\n              \"segmentationId\" : \"segmentationId\"\n            },\n            \"layerProtocol\" : \"IP_OVER_ETHERNET\"\n          } ],\n          \"parentCpConfigId\" : \"parentCpConfigId\",\n          \"createExtLinkPort\" : true\n        }\n      }\n    }, {\n      \"cpConfig\" : {\n        \"key\" : {\n          \"cpProtocolData\" : [ {\n            \"ipOverEthernet\" : {\n              \"macAddress\" : \"macAddress\",\n              \"segmentationType\" : \"VLAN\",\n              \"ipAddresses\" : [ {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              }, {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              } ],\n              \"segmentationId\" : \"segmentationId\"\n            },\n            \"layerProtocol\" : \"IP_OVER_ETHERNET\"\n          }, {\n            \"ipOverEthernet\" : {\n              \"macAddress\" : \"macAddress\",\n              \"segmentationType\" : \"VLAN\",\n              \"ipAddresses\" : [ {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              }, {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              } ],\n              \"segmentationId\" : \"segmentationId\"\n            },\n            \"layerProtocol\" : \"IP_OVER_ETHERNET\"\n          } ],\n          \"parentCpConfigId\" : \"parentCpConfigId\",\n          \"createExtLinkPort\" : true\n        }\n      }\n    } ]\n  } ],\n  \"removeResources\" : [ null, null ],\n  \"zones\" : [ {\n    \"id\" : \"id\"\n  }, {\n    \"id\" : \"id\"\n  } ],\n  \"addResources\" : [ {\n    \"reservationId\" : \"reservationId\"\n  }, {\n    \"reservationId\" : \"reservationId\"\n  } ],\n  \"id\" : \"id\",\n  \"updateResources\" : [ null, null ],\n  \"vimAssets\" : {\n    \"softwareImages\" : [ { }, { } ],\n    \"computeResourceFlavours\" : [ {\n      \"vnfdVirtualComputeDescId\" : \"vnfdVirtualComputeDescId\"\n    }, {\n      \"vnfdVirtualComputeDescId\" : \"vnfdVirtualComputeDescId\"\n    } ],\n    \"snapshotResources\" : [ {\n      \"storageSnapshotId\" : \"\"\n    }, {\n      \"storageSnapshotId\" : \"\"\n    } ]\n  }\n}",
							Grant.class), HttpStatus.NOT_IMPLEMENTED);
				} catch (final IOException e) {
					log.error("Couldn't serialize response for content type application/json", e);
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		} else {
			log.warn("ObjectMapper or HttpServletRequest not configured in default GrantsApi interface so no example is generated");
		}
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}

	@Operation(summary = "", description = "The POST method requests a grant for a particular VNF lifecycle operation. See clause 9.4.2.3.1. ", tags = {})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "201 CREATED  Shall be returned when the grant has been created successfully (synchronous mode). A representation of the created \"Individual grant\" resource shall be returned in the response body. The HTTP response shall include a \"Location\" HTTP header that indicates the URI of the \"Individual grant\" resource just created. ", content = @Content(schema = @Schema(implementation = Grant.class))),

			@ApiResponse(responseCode = "202", description = "202 ACCEPTED  Shall be returned when the request has been accepted for processing and it is expected to take some time to create the grant (asynchronous mode). The response body shall be empty. The HTTP response shall include a \"Location\" HTTP header that indicates the URI of the \"Individual grant\" resource that will be created once the granting decision has been made. "),

			@ApiResponse(responseCode = "400", description = "400 BAD REQUEST 400 code can be returned in the following specified cases, the specific cause has to be proper specified in the \"ProblemDetails\" structure to be returned. If the request is malformed or syntactically incorrect (e.g. if the request URI contains incorrect query parameters or the payload body contains a syntactically incorrect data structure), the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided, and should include in the \"detail\" attribute more information about the source of the problem. If the response to a GET request which queries a container resource would be so big that the performance of the API producer is adversely affected, and the API producer does not support paging for the affected resource, it shall respond with this response code. The \"ProblemDetails\" structure shall be provided, and should include in the \"detail\" attribute more information about the source of the problem. If there is an application error related to the client's input that cannot be easily mapped to any other HTTP response code (\"catch all error\"), the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided, and shall include in the \"detail\" attribute more information about the source of the problem. If the request contains a malformed access token, the API producer should respond with this response. The details of the error shall be returned in the WWW Authenticate HTTP header, as defined in IETF RFC 6750 and IETF RFC 7235. The ProblemDetails structure may be provided. The use of this HTTP error response code described above is applicable to the use of the OAuth 2.0 for the authorization of API requests and notifications, as defined in clauses 4.5.3.3 and 4.5.3.4. ", content = @Content(schema = @Schema(implementation = ProblemDetails2.class))),

			@ApiResponse(responseCode = "401", description = "401 UNAUTHORIZED If the request contains no access token even though one is required, or if the request contains an authorization token that is invalid (e.g. expired or revoked), the API producer should respond with this response. The details of the error shall be returned in the WWW-Authenticate HTTP header, as defined in IETF RFC 6750 and IETF RFC 7235. The ProblemDetails structure may be provided. ", content = @Content(schema = @Schema(implementation = ProblemDetails2.class))),

			@ApiResponse(responseCode = "403", description = "403 FORBIDDEN  Shall be returned upon the following error: The grant has been rejected. A ProblemDetails structure shall be included in the response to provide more details about the rejection in the \"details\" attribute. ", content = @Content(schema = @Schema(implementation = ProblemDetails2.class))),

			@ApiResponse(responseCode = "404", description = "404 NOT FOUND If the API producer did not find a current representation for the resource addressed by the URI passed in the request or is not willing to disclose that one exists, it shall respond with this response code. The \"ProblemDetails\" structure may be provided, including in the \"detail\" attribute information about the source of the problem, e.g. a wrong resource URI variable. This response code is not appropriate in case the resource addressed by the URI is a container resource which is designed to contain child resources, but does not contain any child resource at the time the request is received. For a GET request to an existing empty container resource, a typical response contains a 200 OK response code and a payload body with an empty array. ", content = @Content(schema = @Schema(implementation = ProblemDetails2.class))),

			@ApiResponse(responseCode = "405", description = "405 METHOD NOT ALLOWED If a particular HTTP method is not supported for a particular resource, the API producer shall respond with this response code. The \"ProblemDetails\" structure may be omitted. ", content = @Content(schema = @Schema(implementation = ProblemDetails2.class))),

			@ApiResponse(responseCode = "406", description = "406 NOT ACCEPTABLE If the \"Accept\" HTTP header does not contain at least one name of a content type that is acceptable to the API producer, the API producer shall respond with this response code. The \"ProblemDetails\" structure may be omitted. ", content = @Content(schema = @Schema(implementation = ProblemDetails2.class))),

			@ApiResponse(responseCode = "422", description = "422 UNPROCESSABLE ENTITY If the payload body of a request contains syntactically correct data (e.g. well-formed JSON) but the data cannot be processed (e.g. because it fails validation against a schema), the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided, and should include in the \"detail\" attribute more information about the source of the problem. This error response code is only applicable for methods that have a request body. ", content = @Content(schema = @Schema(implementation = ProblemDetails2.class))),

			@ApiResponse(responseCode = "500", description = "500 INTERNAL SERVER ERROR If there is an application error not related to the client's input that cannot be easily mapped to any other HTTP response code (\"catch all error\"), the API producer shall respond with this response code. The \"ProblemDetails\" structure shall be provided, and shall include in the \"detail\" attribute more information about the source of the problem. ", content = @Content(schema = @Schema(implementation = ProblemDetails2.class))),

			@ApiResponse(responseCode = "503", description = "503 SERVICE UNAVAILABLE If the API producer encounters an internal overload situation of itself or of a system it relies on, it should respond with this response code, following the provisions in IETF RFC 7231 for the use of the \"Retry-After\" HTTP header and for the alternative to refuse the connection. The \"ProblemDetails\" structure may be omitted. ", content = @Content(schema = @Schema(implementation = ProblemDetails2.class))),

			@ApiResponse(responseCode = "504", description = "504 GATEWAY TIMEOUT If the API producer encounters a timeout while waiting for a response from an upstream server (i.e. a server that the API producer communicates with when fulfilling a request), it should respond with this response code. ", content = @Content(schema = @Schema(implementation = ProblemDetails2.class))) })
	@RequestMapping(value = "/grants", produces = { "application/json" }, consumes = { "application/json" }, method = RequestMethod.POST)
	default ResponseEntity<Grant> grantsPost(@Parameter(in = ParameterIn.HEADER, description = "Content-Types that are acceptable for the response. Reference: IETF RFC 7231. ", required = true, schema = @Schema()) @RequestHeader(value = "Accept", required = true) final String accept, @Parameter(in = ParameterIn.HEADER, description = "Version of the API requested to use when responding to this request. ", required = true, schema = @Schema()) @RequestHeader(value = "Version", required = true) final String version, @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody final GrantRequest body, @Parameter(in = ParameterIn.HEADER, description = "The authorization token for the request. Reference: IETF RFC 7235. ", schema = @Schema()) @RequestHeader(value = "Authorization", required = false) final String authorization) {
		if (getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
			if (getAcceptHeader().get().contains("application/json")) {
				try {
					return new ResponseEntity<>(getObjectMapper().get().readValue(
							"{\n  \"extManagedVirtualLinks\" : [ {\n    \"vnfLinkPort\" : [ { }, { } ]\n  }, {\n    \"vnfLinkPort\" : [ { }, { } ]\n  } ],\n  \"vimConnectionInfo\" : {\n    \"key\" : {\n      \"vimType\" : \"vimType\",\n      \"interfaceInfo\" : { }\n    }\n  },\n  \"tempResources\" : [ null, null ],\n  \"zoneGroups\" : [ {\n    \"zoneId\" : [ null, null ]\n  }, {\n    \"zoneId\" : [ null, null ]\n  } ],\n  \"_links\" : {\n    \"self\" : {\n      \"href\" : \"href\"\n    }\n  },\n  \"extVirtualLinks\" : [ {\n    \"extLinkPorts\" : [ {\n      \"resourceHandle\" : {\n        \"vimLevelResourceType\" : \"vimLevelResourceType\"\n      }\n    }, {\n      \"resourceHandle\" : {\n        \"vimLevelResourceType\" : \"vimLevelResourceType\"\n      }\n    } ],\n    \"extCps\" : [ {\n      \"cpConfig\" : {\n        \"key\" : {\n          \"cpProtocolData\" : [ {\n            \"ipOverEthernet\" : {\n              \"macAddress\" : \"macAddress\",\n              \"segmentationType\" : \"VLAN\",\n              \"ipAddresses\" : [ {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              }, {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              } ],\n              \"segmentationId\" : \"segmentationId\"\n            },\n            \"layerProtocol\" : \"IP_OVER_ETHERNET\"\n          }, {\n            \"ipOverEthernet\" : {\n              \"macAddress\" : \"macAddress\",\n              \"segmentationType\" : \"VLAN\",\n              \"ipAddresses\" : [ {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              }, {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              } ],\n              \"segmentationId\" : \"segmentationId\"\n            },\n            \"layerProtocol\" : \"IP_OVER_ETHERNET\"\n          } ],\n          \"parentCpConfigId\" : \"parentCpConfigId\",\n          \"createExtLinkPort\" : true\n        }\n      }\n    }, {\n      \"cpConfig\" : {\n        \"key\" : {\n          \"cpProtocolData\" : [ {\n            \"ipOverEthernet\" : {\n              \"macAddress\" : \"macAddress\",\n              \"segmentationType\" : \"VLAN\",\n              \"ipAddresses\" : [ {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              }, {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              } ],\n              \"segmentationId\" : \"segmentationId\"\n            },\n            \"layerProtocol\" : \"IP_OVER_ETHERNET\"\n          }, {\n            \"ipOverEthernet\" : {\n              \"macAddress\" : \"macAddress\",\n              \"segmentationType\" : \"VLAN\",\n              \"ipAddresses\" : [ {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              }, {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              } ],\n              \"segmentationId\" : \"segmentationId\"\n            },\n            \"layerProtocol\" : \"IP_OVER_ETHERNET\"\n          } ],\n          \"parentCpConfigId\" : \"parentCpConfigId\",\n          \"createExtLinkPort\" : true\n        }\n      }\n    } ]\n  }, {\n    \"extLinkPorts\" : [ {\n      \"resourceHandle\" : {\n        \"vimLevelResourceType\" : \"vimLevelResourceType\"\n      }\n    }, {\n      \"resourceHandle\" : {\n        \"vimLevelResourceType\" : \"vimLevelResourceType\"\n      }\n    } ],\n    \"extCps\" : [ {\n      \"cpConfig\" : {\n        \"key\" : {\n          \"cpProtocolData\" : [ {\n            \"ipOverEthernet\" : {\n              \"macAddress\" : \"macAddress\",\n              \"segmentationType\" : \"VLAN\",\n              \"ipAddresses\" : [ {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              }, {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              } ],\n              \"segmentationId\" : \"segmentationId\"\n            },\n            \"layerProtocol\" : \"IP_OVER_ETHERNET\"\n          }, {\n            \"ipOverEthernet\" : {\n              \"macAddress\" : \"macAddress\",\n              \"segmentationType\" : \"VLAN\",\n              \"ipAddresses\" : [ {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              }, {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              } ],\n              \"segmentationId\" : \"segmentationId\"\n            },\n            \"layerProtocol\" : \"IP_OVER_ETHERNET\"\n          } ],\n          \"parentCpConfigId\" : \"parentCpConfigId\",\n          \"createExtLinkPort\" : true\n        }\n      }\n    }, {\n      \"cpConfig\" : {\n        \"key\" : {\n          \"cpProtocolData\" : [ {\n            \"ipOverEthernet\" : {\n              \"macAddress\" : \"macAddress\",\n              \"segmentationType\" : \"VLAN\",\n              \"ipAddresses\" : [ {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              }, {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              } ],\n              \"segmentationId\" : \"segmentationId\"\n            },\n            \"layerProtocol\" : \"IP_OVER_ETHERNET\"\n          }, {\n            \"ipOverEthernet\" : {\n              \"macAddress\" : \"macAddress\",\n              \"segmentationType\" : \"VLAN\",\n              \"ipAddresses\" : [ {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              }, {\n                \"fixedAddresses\" : [ \"fixedAddresses\", \"fixedAddresses\" ],\n                \"addressRange\" : { },\n                \"type\" : \"IPV4\",\n                \"numDynamicAddresses\" : 0\n              } ],\n              \"segmentationId\" : \"segmentationId\"\n            },\n            \"layerProtocol\" : \"IP_OVER_ETHERNET\"\n          } ],\n          \"parentCpConfigId\" : \"parentCpConfigId\",\n          \"createExtLinkPort\" : true\n        }\n      }\n    } ]\n  } ],\n  \"removeResources\" : [ null, null ],\n  \"zones\" : [ {\n    \"id\" : \"id\"\n  }, {\n    \"id\" : \"id\"\n  } ],\n  \"addResources\" : [ {\n    \"reservationId\" : \"reservationId\"\n  }, {\n    \"reservationId\" : \"reservationId\"\n  } ],\n  \"id\" : \"id\",\n  \"updateResources\" : [ null, null ],\n  \"vimAssets\" : {\n    \"softwareImages\" : [ { }, { } ],\n    \"computeResourceFlavours\" : [ {\n      \"vnfdVirtualComputeDescId\" : \"vnfdVirtualComputeDescId\"\n    }, {\n      \"vnfdVirtualComputeDescId\" : \"vnfdVirtualComputeDescId\"\n    } ],\n    \"snapshotResources\" : [ {\n      \"storageSnapshotId\" : \"\"\n    }, {\n      \"storageSnapshotId\" : \"\"\n    } ]\n  }\n}",
							Grant.class), HttpStatus.NOT_IMPLEMENTED);
				} catch (final IOException e) {
					log.error("Couldn't serialize response for content type application/json", e);
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		} else {
			log.warn("ObjectMapper or HttpServletRequest not configured in default GrantsApi interface so no example is generated");
		}
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}

}
