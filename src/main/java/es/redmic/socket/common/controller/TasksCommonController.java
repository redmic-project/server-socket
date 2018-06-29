package es.redmic.socket.common.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import es.redmic.brokerlib.avro.common.MessageWrapper;

@Controller
public class TasksCommonController extends BaseSocketController {

	@Value("${broker.topic.task.getstatus}")
	private String STATUS_TOPIC;

	@Value("${broker.topic.task.remove}")
	private String REMOVE_TOPIC;

	@MessageMapping("${socket.topic.task.status}")
	public void getTasks(Message<byte[]> message) {

		publishToBroker(STATUS_TOPIC, getMessageWrapper(message));
	}

	@MessageMapping("${socket.topic.task.remove}")
	public void deleteTask(@DestinationVariable String taskId, Message<byte[]> message) {

		MessageWrapper messageWrapper = getMessageWrapper(message);
		messageWrapper.setActionId(taskId);

		publishToBroker(REMOVE_TOPIC, messageWrapper);
	}
}
