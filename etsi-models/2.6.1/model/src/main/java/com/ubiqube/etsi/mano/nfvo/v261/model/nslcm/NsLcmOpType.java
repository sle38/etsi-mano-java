/**
 * This copy of Woodstox XML processor is licensed under the
 * Apache (Software) License, version 2.0 ("the License").
 * See the License for details about distribution rights, and the
 * specific rights regarding derivate works.
 *
 * You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/
 *
 * A copy is also included in the downloadable source code package
 * containing Woodstox, in file "ASL2.0", under the same directory
 * as this file.
 */
package com.ubiqube.etsi.mano.nfvo.v261.model.nslcm;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The enumeration NsLcmOpType represents those lifecycle operations that
 * trigger a NS lifecycle management operation occurrence notification. Value |
 * Description ------|------------ INSTANTIATE | Represents the \"Instantiate
 * NS\" LCM operation. SCALE | Represents the \"Scale NS\" LCM operation. UPDATE
 * | Represents the \"Update NS\" LCM operation. TERMINATE | Represents the
 * \"Terminate NS\" LCM operation. HEAL | Represents the \"Heal NS\" LCM
 * operation.
 */
public enum NsLcmOpType {

	INSTANTIATE("INSTANTIATE"),

	SCALE("SCALE"),

	UPDATE("UPDATE"),

	TERMINATE("TERMINATE"),

	HEAL("HEAL");

	private String value;

	NsLcmOpType(final String value) {
		this.value = value;
	}

	@Override
	@JsonValue
	public String toString() {
		return String.valueOf(value);
	}

	@JsonCreator
	public static NsLcmOpType fromValue(final String text) {
		for (final NsLcmOpType b : NsLcmOpType.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}
}
