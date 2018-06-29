package es.redmic.socket.ingest.data.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import es.redmic.brokerlib.avro.common.MessageWrapper;
import es.redmic.socket.common.controller.BaseSocketController;

@Controller
public class IngestDataDocumentController extends BaseSocketController {

	@Value("${broker.topic.task.ingest.document.run}")
	private String RUN_TOPIC;

	@Value("${broker.topic.task.ingest.document.resume}")
	private String RESUME_TOPIC;

	@Autowired
	public IngestDataDocumentController() {
	}

	@MessageMapping("${socket.topic.task.ingest.document.run}")
	public void register(Message<byte[]> message) {

		publishToBroker(RUN_TOPIC, getMessageWrapper(message));
	}

	@MessageMapping("${socket.topic.task.ingest.document.resume}")
	public void resume(Message<byte[]> message, @DestinationVariable String taskId) {

		MessageWrapper messageWrapper = getMessageWrapper(message);
		messageWrapper.setActionId(taskId);

		publishToBroker(RESUME_TOPIC, messageWrapper);
	}
}