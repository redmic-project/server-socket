package es.redmic.socket.worms;

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
public class WormsToRedmicController extends BaseSocketController {

	@Value("${broker.topic.task.worms.run}")
	private String RUN_TOPIC;

	@Autowired
	public WormsToRedmicController() {
	}

	@MessageMapping("${socket.topic.task.worms.run}")
	public void register(Message<byte[]> message) {

		publishToBroker(RUN_TOPIC, getMessageWrapper(message));
	}
}
