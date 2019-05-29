package org.tron.core.netlog.slf4j_kafka.kafka_producer;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.tron.core.netlog.slf4j_kafka.config.Slf4jKafkaConfig;

public class KafkaProducerContext {

  private static KafkaProducer kafkaProducer;

  public static void init(Slf4jKafkaConfig slf4jKafkaConfig) {
    if (kafkaProducer == null) {
      Properties props = new Properties();
      props.put("bootstrap.servers", slf4jKafkaConfig.getBootstrapServers());
      //props.put("acks", "all");
      props.put("retries", 0);
      props.put("batch.size", 16384);
      props.put("linger.ms", 1);
      props.put("buffer.memory", 33554432);
      props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
      props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

      kafkaProducer = new KafkaProducer(props);
    }
  }

  public static KafkaProducer getKafkaProducer() {
    return kafkaProducer;
  }
}
