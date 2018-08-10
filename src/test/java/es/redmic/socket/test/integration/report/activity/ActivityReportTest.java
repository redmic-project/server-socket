package es.redmic.socket.test.integration.report.activity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.redmic.socket.test.integration.report.common.ReportBaseTest;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = { "schema.registry.port=18087" })
public class ActivityReportTest extends ReportBaseTest {

	// @formatter:off
	
	final String WEBSOCKET_SEND_RUN_TASK = "/socket/tasks/report/activity/run",
			USER_REPORT_STATUS_SUBSCRIBE = "/user/" + userId + "/socket/tasks/report/status/activity";
	// @formatter:on

	@Configuration
	static class ContextConfiguration {

		@Bean
		public ActivityReportBrokerListenerTest activityReportBrokerListenerTest() {
			return new ActivityReportBrokerListenerTest();
		}
	}

	@Test
	public void sendRunTask_ReceiveMessageStatus_IfBrokerIsListen() throws Exception {

		runTaskTest(WEBSOCKET_SEND_RUN_TASK, USER_REPORT_STATUS_SUBSCRIBE);
	}
}