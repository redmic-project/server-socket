package es.redmic.socket.realtime.tracking.vessels;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import es.redmic.brokerlib.avro.common.MessageWrapper;
import es.redmic.socket.common.controller.BaseBrokerListener;

@Component
public class VesselsBrokerListener extends BaseBrokerListener {

	@Value("${socket.publish.realtime.tracking.vessels}")
	protected String PUBLISHING_CHANNEL;

	@KafkaListener(topics = "${broker.topic.realtime.tracking.vessels}")
	public void status(MessageWrapper payload) {

		logger.info("received payload='{}'", payload);
		publish(payload, PUBLISHING_CHANNEL);
	}
}