package org.tron.core.netlog.slf4j_kafka.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import org.tron.core.netlog.slf4j_kafka.kafka_producer.KafkaProducerConcurrentProcessor;

public class KafkaConcurrentAppender<E> extends KafkaAppender<E> {

  public KafkaConcurrentAppender() throws Exception {
    super();
  }

  @Override
  protected void subAppend(E event) {
    LayoutWrappingEncoder<E> encoder = (LayoutWrappingEncoder<E>) this.getEncoder();
    String msg = encoder.getLayout().doLayout(event);
    KafkaProducerConcurrentProcessor.send(slf4jKafkaConfig, msg);
  }
}