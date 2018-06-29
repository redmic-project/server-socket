package es.redmic.socket.test.integration.report.common;

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