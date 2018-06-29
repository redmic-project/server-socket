package es.redmic.socket.test.integration.ingest.common;

import org.springframework.beans.factory.annotation.Value;

import es.redmic.socket.test.integration.common.BrokerListenerBaseTest;

public abstract class IngestBaseBrokerListenerTest extends BrokerListenerBaseTest {

	@Value("${broker.topic.task.ingest.status}")
	protected String INGEST_STATUS_TOPIC;
}
