package es.redmic.socket.report.document;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import es.redmic.brokerlib.avro.common.MessageWrapper;
import es.redmic.socket.common.controller.BaseBrokerListener;

@Component
public class DocumentReportBrokerListener extends BaseBrokerListener {

	@Value("${socket.publish.report.status.document}")
	protected String PUBLISHING_CHANNEL;

	@KafkaListener(topics = "${broker.topic.task.report.document.status}")
	public void status(MessageWrapper payload) {

		logger.info("received payload='{}'", payload);
		publishToUser(payload, PUBLISHING_CHANNEL);
	}
}