/*
 * Copyright 2018 FZI Forschungszentrum Informatik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.streampipes.connect.firstconnector.pipeline.elements;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.streampipes.connect.firstconnector.pipeline.AdapterPipelineElement;
import org.streampipes.messaging.kafka.SpKafkaProducer;

import java.util.Map;

public class SendToKafkaAdapterSink implements AdapterPipelineElement  {
    private SpKafkaProducer producer;
    private ObjectMapper objectMapper;

    public SendToKafkaAdapterSink(String brokerUrl, String topic) {
        producer = new SpKafkaProducer(brokerUrl, topic);
        objectMapper = new ObjectMapper();
    }

    @Override
    public Map<String, Object> process(Map<String, Object> event) {
        try {
            if (event != null) {
                producer.publish(objectMapper.writeValueAsBytes(event));
                System.out.println("send to kafka: " + event);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}