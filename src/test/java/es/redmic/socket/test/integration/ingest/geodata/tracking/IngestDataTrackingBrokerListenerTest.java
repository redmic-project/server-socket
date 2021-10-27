package es.redmic.socket.test.integration.ingest.geodata.tracking;

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

import org.springframework.kafka.annotation.KafkaListener;

import es.redmic.brokerlib.avro.common.MessageWrapper;
import es.redmic.socket.test.integration.ingest.common.IngestBaseBrokerListenerTest;

public class IngestDataTrackingBrokerListenerTest extends IngestBaseBrokerListenerTest {

	public IngestDataTrackingBrokerListenerTest() {
	}

	@KafkaListener(topics = "${broker.topic.task.ingest.tracking.run}")
	public void run(MessageWrapper payload) {

		logger.debug("received payload='{}'", payload);
		publishToBroker(INGEST_STATUS_TOPIC, payload);
	}

	@KafkaListener(topics = "${broker.topic.task.ingest.tracking.resume}")
	public void resume(MessageWrapper payload) {

		logger.debug("received payload='{}'", payload);
		publishToBroker(INGEST_STATUS_TOPIC, payload);
	}
}
