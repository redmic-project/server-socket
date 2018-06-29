package es.redmic.socket.test.integration.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;

import es.redmic.brokerlib.avro.common.MessageWrapper;
import es.redmic.socket.common.controller.BaseSocketController;

public abstract class BrokerListenerBaseTest extends BaseSocketController {

	@Value("${broker.topic.task.status}")
	protected String STATUS_TOPIC;

	@KafkaListener(topics = "${broker.topic.task.getstatus}")
	public void status(MessageWrapper payload) {

		logger.info("received payload='{}'", payload);
		publishToBroker(STATUS_TOPIC, payload);
	}

	@KafkaListener(topics = "${broker.topic.task.remove}")
	public void remove(MessageWrapper payload) {

		logger.info("received payload='{}'", payload);
		publishToBroker(STATUS_TOPIC, payload);
	}
}
