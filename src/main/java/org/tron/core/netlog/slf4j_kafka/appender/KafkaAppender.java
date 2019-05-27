package org.tron.core.netlog.slf4j_kafka.appender;

import ch.qos.logback.core.OutputStreamAppender;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import ch.qos.logback.core.status.ErrorStatus;
import org.tron.core.netlog.slf4j_kafka.config.Slf4jKafkaConfig;
import org.tron.core.netlog.slf4j_kafka.config.Slf4jKafkaConfigSimpleFactory;
import org.tron.core.netlog.slf4j_kafka.kafka_producer.KafkaProducerProcessor;

public class KafkaAppender<E> extends OutputStreamAppender<E> {

  protected static Slf4jKafkaConfig slf4jKafkaConfig;

  public KafkaAppender() throws Exception {
    slf4jKafkaConfig = Slf4jKafkaConfigSimpleFactory.getInstance().parse();
  }

  @Override
  public void start() {
    int errors = 0;
    if (slf4jKafkaConfig == null) {
      addStatus(new ErrorStatus("Kafka Config is Invalid!", this));
      errors++;
    }
    // only error free appenders should be activated
    if (errors == 0) {
      started = true;
      KafkaProducerProcessor.init(slf4jKafkaConfig);
    }
  }

  /**
   * This method differentiates RollingFileAppender from its super class.
   */
  @Override
  protected void subAppend(E event) {
    LayoutWrappingEncoder<E> encoder = (LayoutWrappingEncoder<E>) this.getEncoder();
    String msg = encoder.getLayout().doLayout(event);
    KafkaProducerProcessor.send(slf4jKafkaConfig, msg);
  }
}
