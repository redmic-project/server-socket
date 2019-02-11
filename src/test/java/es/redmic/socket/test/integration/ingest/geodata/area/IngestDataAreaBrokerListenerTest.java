package es.redmic.socket.test.integration.ingest.geodata.area;

import org.springframework.kafka.annotation.KafkaListener;

import es.redmic.brokerlib.avro.common.MessageWrapper;
import es.redmic.socket.test.integration.ingest.common.IngestBaseBrokerListenerTest;

public class IngestDataAreaBrokerListenerTest extends IngestBaseBrokerListenerTest {

	public IngestDataAreaBrokerListenerTest() {
	}

	@KafkaListener(topics = "${broker.topic.task.ingest.area.run}")
	public void run(MessageWrapper payload) {

		logger.debug("received payload='{}'", payload);
		publishToBroker(INGEST_STATUS_TOPIC, payload);
	}

	@KafkaListener(topics = "${broker.topic.task.ingest.area.resume}")
	public void resume(MessageWrapper payload) {

		logger.debug("received payload='{}'", payload);
		publishToBroker(INGEST_STATUS_TOPIC, payload);
	}
}
