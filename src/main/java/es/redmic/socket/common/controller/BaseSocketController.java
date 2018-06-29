package es.redmic.socket.common.controller;

import java.nio.ByteBuffer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.redmic.brokerlib.avro.common.MessageWrapper;
import es.redmic.brokerlib.producer.Sender;
import es.redmic.socket.common.service.UserUtilsService;

public abstract class BaseSocketController {

	protected static Logger logger = LogManager.getLogger();

	@Autowired
	UserUtilsService userUtilsService;

	@Autowired
	private Sender sender;

	@Autowired
	ObjectMapper objectMapper;

	protected void publishToBroker(String topic, MessageWrapper message) {

		logger.info("Publicando al broker por el topic: " + topic + ". Mensaje: " + message);
		sender.send(topic, message);
	}

	protected MessageWrapper getMessageWrapper(Message<byte[]> message) {

		MessageWrapper messageWrapper = new MessageWrapper();

		if (message.getPayload() != null && message.getPayload().length > 0)
			messageWrapper.setContent(ByteBuffer.wrap(message.getPayload()));

		messageWrapper.setUserId(getUserId(message));

		return messageWrapper;
	}

	protected String getUserId(Message<byte[]> message) {

		OAuth2Authentication authentication = message.getHeaders().get(SimpMessageHeaderAccessor.USER_HEADER,
				OAuth2Authentication.class);
		userUtilsService.setAuthentication(authentication);

		return userUtilsService.getUserId();
	}

	@MessageExceptionHandler
	@SendToUser("/queue/errors")
	public String handleException(IllegalStateException ex) {

		logger.info("Error en la comunicación: " + ex.getMessage());
		return ex.getMessage();
	}
}
