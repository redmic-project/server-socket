package es.redmic.socket.test.integration.ingest.data.document;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.redmic.socket.test.integration.ingest.common.IngestBaseTest;

@RunWith(SpringJUnit4ClassRunner.class)
public class IngestDataDocumentTest extends IngestBaseTest {

	// @formatter:off
	
	final String WEBSOCKET_SEND_RUN_TASK = "/socket/tasks/ingest/document/run",
			WEBSOCKET_SEND_MATCHING = "/socket/tasks/ingest/document/resume/";
	// @formatter:on

	@Configuration
	static class ContextConfiguration {

		@Bean
		public IngestDataDocumentBrokerListenerTest ingestDataDocumentBrokerListenerTest() {
			return new IngestDataDocumentBrokerListenerTest();
		}
	}

	@Test
	public void sendRunTask_ReceiveMessageStatus_IfBrokerIsListen() throws Exception {

		runTaskTest(WEBSOCKET_SEND_RUN_TASK);
	}

	@Test
	public void sendResumeTask_ReceiveMessageStatus_IfBrokerIsListen() throws Exception {

		resumeTaskTest(WEBSOCKET_SEND_MATCHING);
	}
}