package es.redmic.socket.test.integration.ingest.series.objectcollectingseries;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.redmic.socket.test.integration.ingest.common.IngestBaseTest;

@RunWith(SpringJUnit4ClassRunner.class)
public class IngestDataObjectCollectingSeriesTest extends IngestBaseTest {

	// @formatter:off
	
	final String WEBSOCKET_SEND_RUN_TASK = "/socket/tasks/ingest/objectcollectingseries/run",
			WEBSOCKET_SEND_MATCHING = "/socket/tasks/ingest/objectcollectingseries/resume/";
	// @formatter:on

	@Configuration
	static class ContextConfiguration {

		@Bean
		public IngestDataObjectCollectingSeriesBrokerListenerTest ingestDataObjectCollectingSeriesBrokerListenerTest() {
			return new IngestDataObjectCollectingSeriesBrokerListenerTest();
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