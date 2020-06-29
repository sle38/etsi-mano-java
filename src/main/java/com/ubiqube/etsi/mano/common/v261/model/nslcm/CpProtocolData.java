package com.ubiqube.etsi.mano.common.v261.model.nslcm;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * This type represents network protocol data.
 */
@ApiModel(description = "This type represents network protocol data. ")
@Validated
public class CpProtocolData {
	/**
	 * Identifier of layer(s) and protocol(s). This attribute allows to signal the
	 * addition of further types of layer and protocol in future versions of the
	 * present document in a backwards-compatible way. In the current version of the
	 * present document, only IP over Ethernet is supported.
	 */
	public enum LayerProtocolEnum {
		IP_OVER_ETHERNET("IP_OVER_ETHERNET");

		private final String value;

		LayerProtocolEnum(final String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static LayerProtocolEnum fromValue(final String text) {
			for (final LayerProtocolEnum b : LayerProtocolEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("layerProtocol")
	private LayerProtocolEnum layerProtocol = null;

	@JsonProperty("ipOverEthernet")
	private IpOverEthernetAddressData ipOverEthernet = null;

	public CpProtocolData layerProtocol(final LayerProtocolEnum _layerProtocol) {
		this.layerProtocol = _layerProtocol;
		return this;
	}

	/**
	 * Identifier of layer(s) and protocol(s). This attribute allows to signal the
	 * addition of further types of layer and protocol in future versions of the
	 * present document in a backwards-compatible way. In the current version of the
	 * present document, only IP over Ethernet is supported.
	 *
	 * @return layerProtocol
	 **/
	@ApiModelProperty(required = true, value = "Identifier of layer(s) and protocol(s). This attribute allows to signal the addition of further types of layer and protocol in future versions of the present document in a backwards-compatible way. In the current version of the present document, only IP over Ethernet is supported. ")
	@NotNull

	public LayerProtocolEnum getLayerProtocol() {
		return layerProtocol;
	}

	public void setLayerProtocol(final LayerProtocolEnum layerProtocol) {
		this.layerProtocol = layerProtocol;
	}

	public CpProtocolData ipOverEthernet(final IpOverEthernetAddressData _ipOverEthernet) {
		this.ipOverEthernet = _ipOverEthernet;
		return this;
	}

	/**
	 * Network address data for IP over Ethernet to assign to the extCP instance.
	 * Shall be present if layerProtocol is equal to \"IP_OVER_ETHERNET\", and shall
	 * be absent otherwise.
	 *
	 * @return ipOverEthernet
	 **/
	@ApiModelProperty(value = "Network address data for IP over Ethernet to assign to the extCP instance. Shall be present if layerProtocol is equal to \"IP_OVER_ETHERNET\", and shall be absent otherwise. ")

	@Valid

	public IpOverEthernetAddressData getIpOverEthernet() {
		return ipOverEthernet;
	}

	public void setIpOverEthernet(final IpOverEthernetAddressData ipOverEthernet) {
		this.ipOverEthernet = ipOverEthernet;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		final CpProtocolData cpProtocolData = (CpProtocolData) o;
		return Objects.equals(this.layerProtocol, cpProtocolData.layerProtocol) &&
				Objects.equals(this.ipOverEthernet, cpProtocolData.ipOverEthernet);
	}

	@Override
	public int hashCode() {
		return Objects.hash(layerProtocol, ipOverEthernet);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class CpProtocolData {\n");

		sb.append("    layerProtocol: ").append(toIndentedString(layerProtocol)).append("\n");
		sb.append("    ipOverEthernet: ").append(toIndentedString(ipOverEthernet)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private static String toIndentedString(final java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
