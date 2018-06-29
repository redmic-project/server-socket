package es.redmic.socket.test.integration.ingest.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.concurrent.TimeUnit;

import es.redmic.socket.test.integration.common.TaskBaseTest;
import es.redmic.socket.test.integration.ingest.common.dto.MatchingDTO;
import es.redmic.socket.test.integration.ingest.common.dto.RunTaskIngestDataDocumentDTO;

public abstract class IngestBaseTest extends TaskBaseTest {

	protected final String USER_INGEST_STATUS_SUBSCRIBE = "/user/" + userId + "/socket/tasks/ingest/status";

	protected void runTaskTest(String runChannel) throws IllegalArgumentException, InterruptedException {

		// Envía petición para arrancar la tarea

		RunTaskIngestDataDocumentDTO runDTO = new RunTaskIngestDataDocumentDTO();

		session.subscribe(USER_INGEST_STATUS_SUBSCRIBE, new DefaultStompFrameHandler(latch));

		session.send(runChannel, runDTO);

		RunTaskIngestDataDocumentDTO runResultDTO = objectMapper.convertValue(blockingQueue.poll(30, TimeUnit.SECONDS),
				RunTaskIngestDataDocumentDTO.class);

		assertNotNull(runResultDTO);
		assertEquals(runDTO.getFileName(), runResultDTO.getFileName());
		assertEquals(runDTO.getDelimiter(), runResultDTO.getDelimiter());
		assertEquals(runDTO.getTaskName(), runResultDTO.getTaskName());
		assertEquals(runDTO.getUserId(), runResultDTO.getUserId());
	}

	protected void resumeTaskTest(String resumeChannel) throws IllegalArgumentException, InterruptedException {

		// Envía intervención de usuario

		MatchingDTO matchingDTO = new MatchingDTO();

		session.subscribe(USER_INGEST_STATUS_SUBSCRIBE, new DefaultStompFrameHandler(latch));

		session.send(resumeChannel + TASK_ID, matchingDTO);

		MatchingDTO matchingResultDTO = objectMapper.convertValue(blockingQueue.poll(30, TimeUnit.SECONDS),
				MatchingDTO.class);

		assertNotNull(matchingResultDTO);
		assertEquals(matchingDTO.getItemMatching().getColumns(), matchingResultDTO.getItemMatching().getColumns());
	}
}