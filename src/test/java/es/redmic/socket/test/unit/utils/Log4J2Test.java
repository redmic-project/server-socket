package es.redmic.socket.test.unit.utils;

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
