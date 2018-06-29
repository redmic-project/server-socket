package es.redmic.socket.report.species;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import es.redmic.socket.common.controller.BaseSocketController;

@Controller
public class SpeciesReportController extends BaseSocketController {

	@Value("${broker.topic.task.report.species.run}")
	private String RUN_TOPIC;

	@Autowired
	public SpeciesReportController() {
	}

	@MessageMapping("${socket.topic.task.report.species.run}")
	public void register(Message<byte[]> message) {

		publishToBroker(RUN_TOPIC, getMessageWrapper(message));
	}
}