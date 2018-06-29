package es.redmic.socket.report.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import es.redmic.socket.common.controller.BaseSocketController;

@Controller
public class ProjectReportController extends BaseSocketController {

	@Value("${broker.topic.task.report.project.run}")
	private String RUN_TOPIC;

	@Autowired
	public ProjectReportController() {
	}

	@MessageMapping("${socket.topic.task.report.project.run}")
	public void register(Message<byte[]> message) {

		publishToBroker(RUN_TOPIC, getMessageWrapper(message));
	}
}