package es.redmic.socket.test.integration.ingest.common;

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
