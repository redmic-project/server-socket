package es.redmic.socket.test.integration.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.ClassRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.redmic.socket.SocketApplication;
import es.redmic.socket.test.SocketApplicationTest;
import es.redmic.testutils.oauth.IntegrationTestBase;

@SpringBootTest(classes = { SocketApplication.class,
		SocketApplicationTest.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext
public abstract class SocketIntegrationTestBase extends IntegrationTestBase {

	protected final static String JOB_PARAMETER_KEY = "parameters";

	protected static Logger logger = LogManager.getLogger();

	// En este contexto es necesario definir embeddedKafka para que la
	// configuración se pueda completar
	@ClassRule
	public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1);

	@LocalServerPort
	int port;

	@Autowired
	protected ObjectMapper objectMapper;

	protected final WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

	protected static SockJsClient sockJsClient;

	public static void preSetup() {

		List<Transport> transports = new ArrayList<>();
		transports.add(new WebSocketTransport(new StandardWebSocketClient()));
		RestTemplateXhrTransport xhrTransport = new RestTemplateXhrTransport(new RestTemplate());
		transports.add(xhrTransport);
		sockJsClient = new SockJsClient(transports);
	}

	protected StompSession connect(String uri, StompSessionHandler handler, String token)
			throws InterruptedException, ExecutionException, TimeoutException {

		WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		return stompClient.connect(uri + "?access_token=" + token, this.headers, handler, port).get(5,
				TimeUnit.SECONDS);
	}

	public class AbstractTestSessionHandler extends StompSessionHandlerAdapter {

		private final AtomicReference<Throwable> failure;

		public AbstractTestSessionHandler(AtomicReference<Throwable> failure) {
			this.failure = failure;
		}

		@Override
		public void handleException(StompSession s, StompCommand c, StompHeaders h, byte[] p, Throwable ex) {
			logger.error("Handler exception", ex);
			this.failure.set(ex);
		}

		@Override
		public void handleTransportError(StompSession session, Throwable ex) {

			if (!ex.getMessage().contains("Connection closed")) {
				logger.error("Transport failure", ex);
				this.failure.set(ex);
			}
		}
	}
}
