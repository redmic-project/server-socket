package es.redmic.socket.report.activity;

/*-
 * #%L
 * socket
 * %%
 * Copyright (C) 2019 REDMIC Project / Server
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import es.redmic.socket.common.controller.BaseSocketController;

@Controller
public class ActivityReportController extends BaseSocketController {

	@Value("${broker.topic.task.report.activity.run}")
	private String RUN_TOPIC;

	@Autowired
	public ActivityReportController() {
	}

	@MessageMapping("${socket.topic.task.report.activity.run}")
	public void register(Message<byte[]> message) {

		publishToBroker(RUN_TOPIC, getMessageWrapper(message));
	}
}
