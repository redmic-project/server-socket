package es.redmic.socket.test.integration.report.common;

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

import static org.junit.Assert.assertNotNull;

import java.util.concurrent.TimeUnit;

import es.redmic.socket.test.integration.common.TaskBaseTest;

public abstract class ReportBaseTest extends TaskBaseTest {

	protected void runTaskTest(String runChannel, String subscribeChannel)
			throws IllegalArgumentException, InterruptedException {

		// Envía petición para arrancar la tarea

		session.subscribe(subscribeChannel, new DefaultStompFrameHandler(latch));

		session.send(runChannel, null);

		assertNotNull(blockingQueue.poll(50, TimeUnit.SECONDS));
	}
}
