package es.redmic.socket.test.integration.ingest.series.timeseries;

import org.springframework.kafka.annotation.KafkaListener;

import es.redmic.brokerlib.avro.common.MessageWrapper;
import es.redmic.socket.test.integration.ingest.common.IngestBaseBrokerListenerTest;

public class IngestDataTimeSeriesBrokerListenerTest extends IngestBaseBrokerListenerTest {

	public IngestDataTimeSeriesBrokerListenerTest() {
		logger.info("Creando listeners de ingest timeseries para emular task");
	}

	@KafkaListener(topics = "${broker.topic.task.ingest.timeseries.run}")
	public void run(MessageWrapper payload) {

		logger.info("received payload='{}'", payload);
		publishToBroker(INGEST_STATUS_TOPIC, payload);
	}

	@KafkaListener(topics = "${broker.topic.task.ingest.timeseries.resume}")
	public void resume(MessageWrapper payload) {

		logger.info("received payload='{}'", payload);
		publishToBroker(INGEST_STATUS_TOPIC, payload);
	}
}
