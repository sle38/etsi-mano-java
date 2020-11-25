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
package com.ubiqube.etsi.mano.factory;

import java.util.Date;

import javax.annotation.Nonnull;

import com.ubiqube.etsi.mano.dao.mano.InstantiationStatusType;
import com.ubiqube.etsi.mano.dao.mano.NsLcmOpOccs;
import com.ubiqube.etsi.mano.dao.mano.NsdChangeType;
import com.ubiqube.etsi.mano.dao.mano.NsdInstance;
import com.ubiqube.etsi.mano.exception.NotFoundException;

public final class LcmFactory {
	private LcmFactory() {
		// Nothing.
	}

	@Nonnull
	public static NsLcmOpOccs createNsLcmOpOcc(final NsdInstance nsInstance, final NsdChangeType lcmOperationType) {
		final NsLcmOpOccs nsLcmOpOccsNsLcmOpOcc = new NsLcmOpOccs();
		nsLcmOpOccsNsLcmOpOcc.setIsAutomaticInvocation(Boolean.TRUE);
		nsLcmOpOccsNsLcmOpOcc.setIsCancelPending(Boolean.FALSE);
		nsLcmOpOccsNsLcmOpOcc.setLcmOperationType(lcmOperationType);
		nsLcmOpOccsNsLcmOpOcc.setNsInstance(nsInstance);
		nsLcmOpOccsNsLcmOpOcc.setOperationParams(lcmOperationTypeToParameter(lcmOperationType));
		nsLcmOpOccsNsLcmOpOcc.setOperationState(InstantiationStatusType.PROCESSING);
		nsLcmOpOccsNsLcmOpOcc.setStartTime(new Date());
		nsLcmOpOccsNsLcmOpOcc.setStateEnteredTime(new Date());
		return nsLcmOpOccsNsLcmOpOcc;
	}

	public static NsdChangeType lcmOperationTypeToParameter(final NsdChangeType lcmOperationType) {
		switch (lcmOperationType) {
		case HEAL:
			return NsdChangeType.HEAL;
		case INSTANTIATE:
			return NsdChangeType.INSTANTIATE;
		case SCALE:
			return NsdChangeType.SCALE;
		case TERMINATE:
			return NsdChangeType.TERMINATE;
		case UPDATE:
			return NsdChangeType.UPDATE;

		default:
			throw new NotFoundException("Unknwon LVM Operation: " + lcmOperationType);
		}
	}

}
