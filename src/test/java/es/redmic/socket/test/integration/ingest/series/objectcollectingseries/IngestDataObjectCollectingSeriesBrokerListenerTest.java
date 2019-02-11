package es.redmic.socket.test.integration.ingest.series.objectcollectingseries;

import org.springframework.kafka.annotation.KafkaListener;

import es.redmic.brokerlib.avro.common.MessageWrapper;
import es.redmic.socket.test.integration.ingest.common.IngestBaseBrokerListenerTest;

public class IngestDataObjectCollectingSeriesBrokerListenerTest extends IngestBaseBrokerListenerTest {

	public IngestDataObjectCollectingSeriesBrokerListenerTest() {
	}

	@KafkaListener(topics = "${broker.topic.task.ingest.objectcollectingseries.run}")
	public void run(MessageWrapper payload) {

		logger.debug("received payload='{}'", payload);
		publishToBroker(INGEST_STATUS_TOPIC, payload);
	}

	@KafkaListener(topics = "${broker.topic.task.ingest.objectcollectingseries.resume}")
	public void resume(MessageWrapper payload) {

		logger.debug("received payload='{}'", payload);
		publishToBroker(INGEST_STATUS_TOPIC, payload);
	}
}
