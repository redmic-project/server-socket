package es.redmic.socket.test.integration.ingest.data.document;

import org.springframework.kafka.annotation.KafkaListener;

import es.redmic.brokerlib.avro.common.MessageWrapper;
import es.redmic.socket.test.integration.ingest.common.IngestBaseBrokerListenerTest;

public class IngestDataDocumentBrokerListenerTest extends IngestBaseBrokerListenerTest {

	public IngestDataDocumentBrokerListenerTest() {
	}

	@KafkaListener(topics = "${broker.topic.task.ingest.document.run}")
	public void run(MessageWrapper payload) {

		logger.debug("received payload='{}'", payload);
		publishToBroker(INGEST_STATUS_TOPIC, payload);
	}

	@KafkaListener(topics = "${broker.topic.task.ingest.document.resume}")
	public void resume(MessageWrapper payload) {

		logger.debug("received payload='{}'", payload);
		publishToBroker(INGEST_STATUS_TOPIC, payload);
	}
}
