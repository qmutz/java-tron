package org.tron.core.netlog.slf4j_kafka.kafka_producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.TimeoutException;
import org.tron.core.netlog.slf4j_kafka.recorder.TronProducerRecord;

@Slf4j(topic = "kafka-log")
public class KafkaProducerConcurrentProcessor {

  public KafkaProducer kafkaProducer;

  public void setKafkaProducer(KafkaProducer kafkaProducer) {
    this.kafkaProducer = kafkaProducer;
  }

  public <K, V> void send(TronProducerRecord<K, V> record) {

    this.kafkaProducer.send(record.getProducerRecord(), new Callback() {
      @Override
      public void onCompletion(RecordMetadata metadata, Exception e) {
        if (e != null) {
          if (e instanceof TimeoutException) {
            // todo
            logger.warn("timeout of kafka server.");
          }
        }

        if (metadata != null) {
          logger.debug("The offset of the record we just sent is: " + metadata.offset());
          logger.debug("The timestamp of the record we just sent is: " + metadata.timestamp());
          logger.debug("The partition of the record we just sent is: " + metadata.partition());
          logger.debug("The topic of the record we just sent is: " + metadata.topic());
        }
      }
    });
  }
}