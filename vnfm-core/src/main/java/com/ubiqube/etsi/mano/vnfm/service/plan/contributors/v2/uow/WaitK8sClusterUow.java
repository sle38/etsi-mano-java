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
package com.ubiqube.etsi.mano.vnfm.service.plan.contributors.v2.uow;

import com.ubiqube.etsi.mano.dao.mano.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.v2.vnfm.OsContainerDeployableTask;
import com.ubiqube.etsi.mano.dao.mano.vnfi.StatusType;
import com.ubiqube.etsi.mano.exception.GenericException;
import com.ubiqube.etsi.mano.orchestrator.Context;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.OsContainerDeployableNode;
import com.ubiqube.etsi.mano.orchestrator.vt.VirtualTask;
import com.ubiqube.etsi.mano.service.vim.K8sStatus;
import com.ubiqube.etsi.mano.service.vim.Vim;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class WaitK8sClusterUow extends AbstractUowV2<OsContainerDeployableTask> {

	private final VimConnectionInformation vimConnectionInformation;
	private final Vim vim;
	private final String toscaName;

	protected WaitK8sClusterUow(final VirtualTask<OsContainerDeployableTask> task, final Vim vim, final VimConnectionInformation vimConnectionInformation) {
		super(task, OsContainerDeployableNode.class);
		this.vim = vim;
		this.vimConnectionInformation = vimConnectionInformation;
		this.toscaName = task.getName();
	}

	@Override
	public String execute(final Context context) {
		final String id = context.get(OsContainerDeployableNode.class, toscaName);
		K8sStatus st = vim.cnf(vimConnectionInformation).k8sStatus(id);
		// XXX
		while (st.getStatus() != StatusType.ADOPT_COMPLETE) {
			try {
				Thread.sleep(1_000L);
			} catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new GenericException(e);
			}
			st = vim.cnf(vimConnectionInformation).k8sStatus(id);
		}
		return null;
	}

	@Override
	public String rollback(final Context context) {
		// TODO Auto-generated method stub
		return null;
	}

}
