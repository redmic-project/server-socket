package es.redmic.socket.test.integration.report.activity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;

import es.redmic.brokerlib.avro.common.MessageWrapper;
import es.redmic.socket.test.integration.report.common.ReportBaseBrokerListenerTest;

public class ActivityReportBrokerListenerTest extends ReportBaseBrokerListenerTest {

	@Value("${broker.topic.task.report.activity.status}")
	protected String REPORT_STATUS_TOPIC;

	public ActivityReportBrokerListenerTest() {
	}

	@KafkaListener(topics = "${broker.topic.task.report.activity.run}")
	public void run(MessageWrapper payload) {

		logger.debug("received payload='{}'", payload);
		publishToBroker(REPORT_STATUS_TOPIC, payload);
	}
}
