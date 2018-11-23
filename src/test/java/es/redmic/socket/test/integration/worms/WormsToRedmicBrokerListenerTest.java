package es.redmic.socket.test.integration.worms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;

import es.redmic.brokerlib.avro.common.MessageWrapper;
import es.redmic.socket.test.integration.common.BrokerListenerBaseTest;

public class WormsToRedmicBrokerListenerTest extends BrokerListenerBaseTest {

	public WormsToRedmicBrokerListenerTest() {
	}

	@Value("${broker.topic.task.worms.status}")
	protected String WORMS_STATUS_TOPIC;

	@KafkaListener(topics = "${broker.topic.task.worms.run}")
	public void run(MessageWrapper payload) {

		logger.debug("received payload='{}'", payload);
		publishToBroker(WORMS_STATUS_TOPIC, payload);
	}
}
