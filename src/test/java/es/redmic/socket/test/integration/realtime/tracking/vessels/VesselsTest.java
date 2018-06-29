package es.redmic.socket.test.integration.realtime.tracking.vessels;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.HashMap;
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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.redmic.brokerlib.avro.common.MessageWrapper;
import es.redmic.brokerlib.producer.Sender;
import es.redmic.socket.test.integration.common.SocketIntegrationTestBase;

@RunWith(SpringJUnit4ClassRunner.class)
public class VesselsTest extends SocketIntegrationTestBase {

	protected Long userId = 13L;

	protected static final String TASK_ID = "33";

	@Autowired
	protected ObjectMapper objectMapper;

	protected CountDownLatch latch;
	AtomicReference<Throwable> failure;

	protected StompSession session;

	@Autowired
	private Sender sender;

	// @formatter:off
	
	protected final String WEBSOCKET_URI = "ws://localhost:{port}/api/socket/msg",
			VESSELS_TRACKING_SUBSCRIBE = "/public/socket/realtime/tracking/vessels",
			TOPIC_VESSELS = "realtime.geodata.tracking.vessels";

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

	@SuppressWarnings("unchecked")
	@Test
	public void sendStatus_ReceiveMessageStatus_IfBrokerIsListen() throws Exception {

		// Envía obtención de status de todas las tareas

		session.subscribe(VESSELS_TRACKING_SUBSCRIBE, new DefaultStompFrameHandler(latch));

		MessageWrapper msg = new MessageWrapper();

		Map<String, String> content = new HashMap<String, String>();
		content.put("dsd", "sads");
		msg.setContent(ByteBuffer.wrap(objectMapper.writeValueAsBytes(content)));
		msg.setUserId("-1");

		sender.send(TOPIC_VESSELS, msg);

		Map<String, String> result = objectMapper.convertValue(blockingQueue.poll(50, TimeUnit.SECONDS), Map.class);
		assertNotNull(result);
		assertEquals(content, result);
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
