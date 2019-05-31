package org.tron.core.netlog.slf4j_kafka.appender;


import ch.qos.logback.core.OutputStreamAppender;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import ch.qos.logback.core.status.ErrorStatus;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.tron.core.netlog.slf4j_kafka.config.Slf4jKafkaParamParse;
import org.tron.core.netlog.slf4j_kafka.exceptions.KafkaConfigException;
import org.tron.core.netlog.slf4j_kafka.exceptions.KafkaProducerCreateException;
import org.tron.core.netlog.slf4j_kafka.kafka_producer.KafkaProducerConcurrentProcessor;
import org.tron.core.netlog.slf4j_kafka.kafka_producer.KafkaProducerContext;
import org.tron.core.netlog.slf4j_kafka.recorder.NetLogRecord;
import org.tron.core.netlog.slf4j_kafka.recorder.TronProducerRecord;

@Slf4j(topic = "kafka-log")
public class KafkaConcurrentAppender<E> extends OutputStreamAppender<E> {

  private String topic;
  private String bootstrapServers;

  private String kafkaParamString;
  private Properties kafkaConfig = new Properties();

  private KafkaProducerConcurrentProcessor processor;

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public String getBootstrapServers() {
    return bootstrapServers;
  }

  public void setBootstrapServers(String bootstrapServers) {
    this.bootstrapServers = bootstrapServers;
  }

  public String getKafkaParamString() {
    return kafkaParamString;
  }

  public void setKafkaParamString(String kafkaParamString) {
    this.kafkaParamString = kafkaParamString;
  }

  @Override
  public void start() {
    int errors = 0;

    if (StringUtils.isEmpty(topic)) {
      addStatus(new ErrorStatus("topic is empty", this));
      errors++;
    }

    if (StringUtils.isEmpty(bootstrapServers)) {
      addStatus(new ErrorStatus("bootstrap.server is empty", this));
      errors++;
    }

    try {
      kafkaConfig = Slf4jKafkaParamParse.INSTANCE.parse(kafkaParamString);
    } catch (KafkaConfigException e) {
      addStatus(new ErrorStatus(e.getMessage(), this));
      errors++;
    }

    try {
      processor = new KafkaProducerConcurrentProcessor();
      kafkaConfig.setProperty("bootstrap.servers", bootstrapServers);
      processor.setKafkaProducer(KafkaProducerContext.makeKafkaProducer(kafkaConfig));
    } catch (KafkaProducerCreateException e) {
      addStatus(new ErrorStatus(e.getMessage(), this));
      errors++;
    }

    if (errors == 0) {
      started = true;
      logger.info("Net Log Startup successfully.");
    }
  }

  @Override
  protected void subAppend(E event) {
    LayoutWrappingEncoder<E> encoder = (LayoutWrappingEncoder<E>) this.getEncoder();
    String msg = encoder.getLayout().doLayout(event);
    TronProducerRecord record = new NetLogRecord(
        this.getTopic(), msg, null,
        null, null);
    processor.send(record);
  }
}