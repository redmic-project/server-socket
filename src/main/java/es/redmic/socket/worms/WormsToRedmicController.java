package es.redmic.socket.worms;

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