package es.redmic.socket.ingest.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import es.redmic.brokerlib.avro.common.MessageWrapper;
import es.redmic.socket.common.controller.BaseBrokerListener;

@Component
public class IngestBrokerListener extends BaseBrokerListener {

	@Value("${socket.publish.ingest.status}")
	protected String PUBLISHING_CHANNEL;

	@KafkaListener(topics = "${broker.topic.task.ingest.status}")
	public void status(MessageWrapper payload) {

		logger.info("received payload='{}'", payload);
		publishToUser(payload, PUBLISHING_CHANNEL);
	}
}