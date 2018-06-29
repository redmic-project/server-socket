package es.redmic.socket.test.integration.report.document;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;

import es.redmic.brokerlib.avro.common.MessageWrapper;
import es.redmic.socket.test.integration.report.common.ReportBaseBrokerListenerTest;

public class DocumentReportBrokerListenerTest extends ReportBaseBrokerListenerTest {

	@Value("${broker.topic.task.report.document.status}")
	protected String REPORT_STATUS_TOPIC;

	public DocumentReportBrokerListenerTest() {
		logger.info("Creando listeners de report document para emular task");
	}

	@KafkaListener(topics = "${broker.topic.task.report.document.run}")
	public void run(MessageWrapper payload) {

		logger.info("received payload='{}'", payload);
		publishToBroker(REPORT_STATUS_TOPIC, payload);
	}
}
