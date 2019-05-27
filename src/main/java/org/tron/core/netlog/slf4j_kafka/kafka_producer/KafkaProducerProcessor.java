package org.tron.core.netlog.slf4j_kafka.kafka_producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.tron.core.netlog.slf4j_kafka.config.Slf4jKafkaConfig;

public class KafkaProducerProcessor {

  public static void send(Slf4jKafkaConfig slf4jKafkaConfig, String msg) {
    KafkaProducer kafkaProducer = KafkaProducerContext.getKafkaProducer();
    KafkaSenderHandler.getInstance(kafkaProducer)
        .send(new ProducerRecord(slf4jKafkaConfig.getKafkaTopic(), msg));
  }

  public static void init(Slf4jKafkaConfig slf4jKafkaConfig) {
    KafkaProducerContext.init(slf4jKafkaConfig);
  }

}
