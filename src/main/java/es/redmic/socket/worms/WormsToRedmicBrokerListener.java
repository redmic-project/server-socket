package es.redmic.socket.worms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import es.redmic.brokerlib.avro.common.MessageWrapper;
import es.redmic.socket.common.controller.BaseBrokerListener;

@Component
public class WormsToRedmicBrokerListener extends BaseBrokerListener {

	@Value("${socket.publish.worms.status}")
	protected String PUBLISHING_CHANNEL;

	@KafkaListener(topics = "${broker.topic.task.worms.status}")
	public void status(MessageWrapper payload) {

		logger.info("received payload='{}'", payload);
		publishToUser(payload, PUBLISHING_CHANNEL);
	}
}