package org.tron.core.netlog.slf4j_kafka.config;

import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Set;

public class Slf4jKafkaConfigSimpleFactory {

  private static Slf4jKafkaConfigSimpleFactory instance;
  private static final String DEFAULT_CONFIGURATION_FILE = "slf4j-kafka.properties";

  private static final String KAFKA_ZK_QUORUM = "kafka.bootstrapServers"; //kafka zeekeeper quorum
  private static final String KAFKA_TOPIC = "kafka.topic";
  private static final String KAFKA_PARAM_PERFIX = "kafka.param.";

  public static Slf4jKafkaConfigSimpleFactory getInstance() {
    if (instance == null) {
      instance = new Slf4jKafkaConfigSimpleFactory();
    }
    return instance;
  }

  public Slf4jKafkaConfig parse() throws Exception {
    Slf4jKafkaConfig config = new Slf4jKafkaConfig();

    Properties props = new Properties();
    props.load(
        new InputStreamReader(
            this.getClass().getClassLoader().getResourceAsStream(DEFAULT_CONFIGURATION_FILE)));
    Set<String> keys = props.stringPropertyNames();
    for (String key : keys) {
      if (KAFKA_ZK_QUORUM.equals(key)) {
        config.setBootstrapServers(props.getProperty(key));
      } else if (KAFKA_TOPIC.equals(key)) {
        config.setKafkaTopic(props.getProperty(key));
      } else if (key.startsWith(KAFKA_PARAM_PERFIX)) {
        config.getParamMap()
            .put(key.replace(KAFKA_PARAM_PERFIX, ""), props.getProperty(key));
      }
    }

    return config;
  }
}
