package es.redmic.socket.common.controller;

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

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.redmic.brokerlib.avro.common.MessageWrapper;
import es.redmic.brokerlib.utils.MessageWrapperUtils;

public abstract class BaseBrokerListener {

	protected static Logger logger = LogManager.getLogger();

	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	protected ObjectMapper objectMapper;

	protected void publishToUser(MessageWrapper payload, String channel) {

		String userId = payload.getUserId();

		Map<String, Object> message = MessageWrapperUtils.getMessageFromMessageWrapper(payload);

		logger.info("Publicando al usuario: " + userId + ". Por el canal: " + channel + ". Mensaje: " + message);
		simpMessagingTemplate.convertAndSendToUser(userId, channel, message);
	}

	protected void publish(MessageWrapper payload, String channel) {

		Map<String, Object> message = MessageWrapperUtils.getMessageFromMessageWrapper(payload);

		logger.info("Publicando broadcast por el canal: " + channel + ". Mensaje: " + message);
		simpMessagingTemplate.convertAndSend(channel, message);
	}

}
