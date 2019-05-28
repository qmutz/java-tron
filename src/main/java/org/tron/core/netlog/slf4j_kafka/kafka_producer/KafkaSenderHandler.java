package org.tron.core.netlog.slf4j_kafka.kafka_producer;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaSenderHandler {

  private static KafkaSenderHandler kafkaSenderHandler;
  private static boolean lock = false;
  private KafkaProducer kafkaProducer;

  public KafkaSenderHandler(KafkaProducer kafkaProducer) {
    this.kafkaProducer = kafkaProducer;
  }

  public static KafkaSenderHandler getInstance(KafkaProducer kafkaProducer) {
    if (kafkaSenderHandler == null) {
      kafkaSenderHandler = new KafkaSenderHandler(kafkaProducer);
      kafkaSenderHandler.start();
    }
    return kafkaSenderHandler;
  }

  private final Queue<ProducerRecord> queue = new ConcurrentLinkedQueue<>();

  public void start() {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.submit(() -> {
      while (true) {
        while (!queue.isEmpty() && !lock) {
          ProducerRecord producerRecord = queue.poll();
          lock = true;
          kafkaProducer.send(producerRecord, (recordMetadata, e) -> {
            lock = false;
          });
        }
        Thread.sleep(50L);
      }
    });
  }

  public void send(ProducerRecord producerRecord) {
    queue.add(producerRecord);
  }

}
