package es.redmic.socket.test.integration.report.program;

/*-
 * #%L
 * socket
 * %%
 * Copyright (C) 2019 REDMIC Project / Server
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.redmic.socket.test.integration.report.common.ReportBaseTest;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = { "schema.registry.port=18089" })
public class ProgramReportTest extends ReportBaseTest {

	// @formatter:off
	
	final String WEBSOCKET_SEND_RUN_TASK = "/socket/tasks/report/program/run",
			USER_REPORT_STATUS_SUBSCRIBE = "/user/" + userId + "/socket/tasks/report/status/program";
	// @formatter:on

	@Configuration
	static class ContextConfiguration {

		@Bean
		public ProgramReportBrokerListenerTest programReportBrokerListenerTest() {
			return new ProgramReportBrokerListenerTest();
		}
	}

	@Test
	public void sendRunTask_ReceiveMessageStatus_IfBrokerIsListen() throws Exception {

		runTaskTest(WEBSOCKET_SEND_RUN_TASK, USER_REPORT_STATUS_SUBSCRIBE);
	}
}
