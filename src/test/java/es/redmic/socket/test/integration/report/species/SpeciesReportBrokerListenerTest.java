package es.redmic.socket.test.integration.report.species;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;

import es.redmic.brokerlib.avro.common.MessageWrapper;
import es.redmic.socket.test.integration.report.common.ReportBaseBrokerListenerTest;

public class SpeciesReportBrokerListenerTest extends ReportBaseBrokerListenerTest {

	@Value("${broker.topic.task.report.species.status}")
	protected String REPORT_STATUS_TOPIC;

	public SpeciesReportBrokerListenerTest() {
		logger.info("Creando listeners de report species para emular task");
	}

	@KafkaListener(topics = "${broker.topic.task.report.species.run}")
	public void run(MessageWrapper payload) {

		logger.info("received payload='{}'", payload);
		publishToBroker(REPORT_STATUS_TOPIC, payload);
	}
}
