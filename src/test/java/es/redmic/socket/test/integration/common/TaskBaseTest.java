package es.redmic.socket.test.integration.common;

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
import static org.junit.Assert.fail;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;

public abstract class TaskBaseTest extends SocketIntegrationTestBase {

	protected Long userId = 13L;

	protected static final String TASK_ID = "33";

	protected CountDownLatch latch;
	AtomicReference<Throwable> failure;

	protected StompSession session;

	// @formatter:off
	
	protected final String WEBSOCKET_URI = "ws://localhost:{port}/api/socket/msg",
			WEBSOCKET_SEND_STATUS = "/socket/tasks/status",
			WEBSOCKET_SEND_REMOVE = "/socket/tasks/remove/",
			USER_STATUS_SUBSCRIBE = "/user/" + userId + "/socket/tasks/status";

	// @formatter:on

	protected static Logger logger = LogManager.getLogger();

	protected static BlockingQueue<Object> blockingQueue;

	@Before
	public void before() throws Exception {

		latch = new CountDownLatch(1);
		failure = new AtomicReference<>();

		session = connect(WEBSOCKET_URI, new AbstractTestSessionHandler(failure), getTokenOAGUser());
	}

	@After
	public void after() throws InterruptedException {

		if (latch.getCount() == 0 || failure.get() != null) {
			session.disconnect();
		}

		if (failure.get() != null) {
			throw new AssertionError(failure.get().getMessage(), failure.get());
		}

		if (!latch.await(2, TimeUnit.MINUTES)) {
			fail("Test timeout");
		}
	}

	@BeforeClass
	public static void setup() {
		preSetup();

		blockingQueue = new LinkedBlockingDeque<>();
	}

	@Test
	public void sendStatus_ReceiveMessageStatus_IfBrokerIsListen() throws Exception {

		// Envía obtención de status de todas las tareas

		session.subscribe(USER_STATUS_SUBSCRIBE, new DefaultStompFrameHandler(latch));

		session.send(WEBSOCKET_SEND_STATUS, null);

		assertNotNull(blockingQueue.poll(15, TimeUnit.SECONDS));
	}

	@Test
	public void sendRemove_ReceiveMessageStatus_IfBrokerIsListen() throws Exception {

		// Envía intervención de usuario

		session.subscribe(USER_STATUS_SUBSCRIBE, new DefaultStompFrameHandler(latch));

		session.send(WEBSOCKET_SEND_REMOVE + TASK_ID, null);

		assertNotNull(blockingQueue.poll(15, TimeUnit.SECONDS));
	}

	public class DefaultStompFrameHandler implements StompFrameHandler {

		CountDownLatch latch;

		public DefaultStompFrameHandler(CountDownLatch latch) {
			this.latch = latch;
		}

		@Override
		public Type getPayloadType(StompHeaders headers) {
			return Map.class;
		}

		@Override
		public void handleFrame(StompHeaders headers, Object payload) {

			logger.info("Recibido mensaje: " + payload);
			latch.countDown();
			blockingQueue.offer(payload);
		}
	}
}
