package es.redmic.socket.test.integration;

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
import static org.junit.Assert.fail;

import java.lang.reflect.Type;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.redmic.socket.test.integration.common.SocketIntegrationTestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = { "schema.registry.port=18093" })
public class WebSocketTest extends SocketIntegrationTestBase {

	Long userId = 15L;

	private static Logger logger = LogManager.getLogger();

	// @formatter:off

	private  String MESSAGE = "Hello",
			TOPIC = "/user/" + userId + "/socket/tasks/ingest/status",
			WEBSOCKET_URI = "ws://localhost:{port}/api/socket/msg";

	// @formatter:on

	static BlockingQueue<String> blockingQueue;

	@BeforeClass
	public static void setup() {
		preSetup();

		blockingQueue = new LinkedBlockingDeque<>();
	}

	@Test
	public void shouldReceiveAMessageFromTheServer() throws Exception {

		final CountDownLatch latch = new CountDownLatch(1);
		final AtomicReference<Throwable> failure = new AtomicReference<>();

		StompSession session = connect(WEBSOCKET_URI, new AbstractTestSessionHandler(failure), getTokenOAGUser());

		session.subscribe(TOPIC, new DefaultStompFrameHandler(latch));

		session.send(TOPIC, MESSAGE);

		assertEquals(MESSAGE, blockingQueue.poll(5, TimeUnit.SECONDS));

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

	class DefaultStompFrameHandler implements StompFrameHandler {

		CountDownLatch latch;

		public DefaultStompFrameHandler(CountDownLatch latch) {
			this.latch = latch;
		}

		@Override
		public Type getPayloadType(StompHeaders headers) {
			return String.class;
		}

		@Override
		public void handleFrame(StompHeaders headers, Object payload) {

			logger.info("This is an message " + payload);
			latch.countDown();
			blockingQueue.offer(payload.toString());
		}
	}
}
