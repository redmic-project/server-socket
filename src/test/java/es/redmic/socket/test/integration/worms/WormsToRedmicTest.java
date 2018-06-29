package es.redmic.socket.test.integration.worms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.redmic.socket.test.integration.common.TaskBaseTest;
import es.redmic.socket.test.integration.ingest.common.dto.RunTaskIngestDataDocumentDTO;

@RunWith(SpringJUnit4ClassRunner.class)
public class WormsToRedmicTest extends TaskBaseTest {

	// @formatter:off
	
	final String WEBSOCKET_SEND_RUN_TASK = "/socket/tasks/wormstoredmic/run",
			USER_WORMS_STATUS_SUBSCRIBE = "/user/" + userId + "/socket/tasks/wormstoredmic/status";
	// @formatter:on

	@Configuration
	static class ContextConfiguration {

		@Bean
		public WormsToRedmicBrokerListenerTest wormsToRedmicBrokerListenerTest() {
			return new WormsToRedmicBrokerListenerTest();
		}
	}

	@Test
	public void sendRunTask_ReceiveMessageStatus_IfBrokerIsListen() throws Exception {

		// Envía petición para arrancar la tarea

		RunTaskIngestDataDocumentDTO runDTO = new RunTaskIngestDataDocumentDTO();

		session.subscribe(USER_WORMS_STATUS_SUBSCRIBE, new DefaultStompFrameHandler(latch));

		session.send(WEBSOCKET_SEND_RUN_TASK, runDTO);

		RunTaskIngestDataDocumentDTO runResultDTO = objectMapper.convertValue(blockingQueue.poll(15, TimeUnit.SECONDS),
				RunTaskIngestDataDocumentDTO.class);

		assertNotNull(runResultDTO);
		assertEquals(runDTO.getFileName(), runResultDTO.getFileName());
		assertEquals(runDTO.getDelimiter(), runResultDTO.getDelimiter());
		assertEquals(runDTO.getTaskName(), runResultDTO.getTaskName());
		assertEquals(runDTO.getUserId(), runResultDTO.getUserId());
	}
}