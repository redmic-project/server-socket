package es.redmic.socket.test.integration.worms;

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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;

import es.redmic.brokerlib.avro.common.MessageWrapper;
import es.redmic.socket.test.integration.common.BrokerListenerBaseTest;

public class WormsToRedmicBrokerListenerTest extends BrokerListenerBaseTest {

	public WormsToRedmicBrokerListenerTest() {
	}

	@Value("${broker.topic.task.worms.status}")
	protected String WORMS_STATUS_TOPIC;

	@KafkaListener(topics = "${broker.topic.task.worms.run}")
	public void run(MessageWrapper payload) {

		logger.debug("received payload='{}'", payload);
		publishToBroker(WORMS_STATUS_TOPIC, payload);
	}
}
