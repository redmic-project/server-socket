package es.redmic.socket.test.unit.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class Log4J2Test {

	// TODO: Si se configura fichero, testear que se crea y el contenido es el
	// correcto.

	@Test
	public void testPerformSomeTask() throws Exception {
		Log4J2YamlConf log4J2YamlConf = new Log4J2YamlConf();
		log4J2YamlConf.performSomeTask();
	}

	public static class Log4J2YamlConf {
		private static Logger logger = LogManager.getLogger();

		public void performSomeTask() {
			logger.debug("This is a debug message");
			logger.info("This is an info message");
			logger.warn("This is a warn message");
			logger.error("This is an error message");
			logger.fatal("This is a fatal message");
		}
	}
}
