package es.redmic.socket.ingest.geodata.tracking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import es.redmic.brokerlib.avro.common.MessageWrapper;
import es.redmic.socket.common.controller.BaseSocketController;

@Controller
public class IngestDataTrackingController extends BaseSocketController {

	@Value("${broker.topic.task.ingest.tracking.run}")
	private String RUN_TOPIC;

	@Value("${broker.topic.task.ingest.tracking.resume}")
	private String RESUME_TOPIC;

	@Autowired
	public IngestDataTrackingController() {
	}

	@MessageMapping("${socket.topic.task.ingest.tracking.run}")
	public void register(Message<byte[]> message) {

		publishToBroker(RUN_TOPIC, getMessageWrapper(message));
	}

	@MessageMapping("${socket.topic.task.ingest.tracking.resume}")
	public void resume(Message<byte[]> message, @DestinationVariable String taskId) {

		MessageWrapper messageWrapper = getMessageWrapper(message);
		messageWrapper.setActionId(taskId);

		publishToBroker(RESUME_TOPIC, messageWrapper);
	}
}