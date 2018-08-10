package es.redmic.socket.test.integration.ingest.series.timeseries;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.redmic.socket.test.integration.ingest.common.IngestBaseTest;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = { "schema.registry.port=18085" })
public class IngestDataTimeSeriesTest extends IngestBaseTest {

	// @formatter:off
	
	final String WEBSOCKET_SEND_RUN_TASK = "/socket/tasks/ingest/timeseries/run",
			WEBSOCKET_SEND_MATCHING = "/socket/tasks/ingest/timeseries/resume/";
	// @formatter:on

	@Configuration
	static class ContextConfiguration {

		@Bean
		public IngestDataTimeSeriesBrokerListenerTest ingestDataTimeSeriesBrokerListenerTest() {
			return new IngestDataTimeSeriesBrokerListenerTest();
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