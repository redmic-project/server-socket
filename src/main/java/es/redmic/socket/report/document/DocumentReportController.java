package es.redmic.socket.report.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import es.redmic.socket.common.controller.BaseSocketController;

@Controller
public class DocumentReportController extends BaseSocketController {

	@Value("${broker.topic.task.report.document.run}")
	private String RUN_TOPIC;

	@Autowired
	public DocumentReportController() {
	}

	@MessageMapping("${socket.topic.task.report.document.run}")
	public void register(Message<byte[]> message) {

		publishToBroker(RUN_TOPIC, getMessageWrapper(message));
	}
}