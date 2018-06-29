package es.redmic.socket.test.integration.ingest.geodata.tracking;

import org.springframework.kafka.annotation.KafkaListener;

import es.redmic.brokerlib.avro.common.MessageWrapper;
import es.redmic.socket.test.integration.ingest.common.IngestBaseBrokerListenerTest;

public class IngestDataTrackingBrokerListenerTest extends IngestBaseBrokerListenerTest {

	public IngestDataTrackingBrokerListenerTest() {
		logger.info("Creando listeners de ingest tracking para emular task");
	}

	@KafkaListener(topics = "${broker.topic.task.ingest.tracking.run}")
	public void run(MessageWrapper payload) {

		logger.info("received payload='{}'", payload);
		publishToBroker(INGEST_STATUS_TOPIC, payload);
	}

	@KafkaListener(topics = "${broker.topic.task.ingest.tracking.resume}")
	public void resume(MessageWrapper payload) {

		logger.info("received payload='{}'", payload);
		publishToBroker(INGEST_STATUS_TOPIC, payload);
	}
}
