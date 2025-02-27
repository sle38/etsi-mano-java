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
package com.ubiqube.etsi.mano.service.event.quartz;

import java.util.Map;
import java.util.UUID;

import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.model.EventMessage;
import com.ubiqube.etsi.mano.model.NotificationEvent;
import com.ubiqube.etsi.mano.service.event.ActionMessage;
import com.ubiqube.etsi.mano.service.event.ActionType;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class QuartzEventUtils {

	private static final String OBJECT_ID = "objectId";
	private static final String EVENT_TYPE = "eventType";
	private static final Logger LOG = LoggerFactory.getLogger(QuartzEventUtils.class);

	private QuartzEventUtils() {
		// Nothing.
	}

	public static <U> JobDataMap createJobMap(final UUID id, final String actionType, final UUID objectId, final Map<String, U> parameters) {
		final JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("id", id);
		jobDataMap.put(EVENT_TYPE, actionType);
		jobDataMap.put(OBJECT_ID, objectId);
		return jobDataMap;
	}

	public static ActionMessage createActionMessage(final JobDataMap jobDataMap) {
		final ActionType eventType = ActionType.valueOf(jobDataMap.getString(EVENT_TYPE));
		final UUID objectId = (UUID) jobDataMap.get(OBJECT_ID);
		LOG.info("Quartz event start {} / {}", eventType, objectId);
		if (null == objectId) {
			throw new IllegalArgumentException("Event received With no ObjectId.");
		}
		return new ActionMessage(eventType, objectId, jobDataMap);
	}

	public static EventMessage createEventMessage(final JobDataMap jobDataMap) {
		final NotificationEvent eventType = NotificationEvent.valueOf(jobDataMap.getString(EVENT_TYPE));
		final UUID objectId = (UUID) jobDataMap.get(OBJECT_ID);
		final EventMessage ev = new EventMessage(eventType, objectId, Map.of());
		ev.setId((UUID) jobDataMap.get("id"));
		return ev;
	}
}
