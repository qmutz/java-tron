package org.tron.core.netlog.slf4j_kafka.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;
import java.util.Set;

public class Slf4jKafkaConfigSimpleFactory {

  private static Slf4jKafkaConfigSimpleFactory instance;
  private static final String DEFAULT_CONFIGURATION_FILE = "slf4j-kafka.properties";//默认配置文件

  private static final String KAFKA_ZK_QUORUM = "kafka.bootstrapServers"; //kafka zeekeeper quorum
  private static final String KAFKA_TOPIC = "kafka.topic";  //kafka topic
  private static final String KAFKA_APPID = "kafka.appId";  //kafka topic
  private static final String KAFKA_LAYOUT_CONVERSION_PATTERN = "kafka.layout.ConversionPattern"; //消息格式
  private static final String KAFKA_PARAM_PERFIX = "kafka.param."; //自定义参数前缀

  public static Slf4jKafkaConfigSimpleFactory getInstance() {
    if (instance == null) {
      instance = new Slf4jKafkaConfigSimpleFactory();
    }
    return instance;
  }

  private URL getConfigFileUrl() {
    ClassLoader myClassLoader = Slf4jKafkaConfigSimpleFactory.class.getClassLoader();
    try {
      return myClassLoader.getResource(DEFAULT_CONFIGURATION_FILE);
    } catch (Throwable t) {
      return null;
    }
  }

  public Slf4jKafkaConfig parse() throws Exception {
    Slf4jKafkaConfig config = new Slf4jKafkaConfig();
    URL configFileUrl = getConfigFileUrl();
    if (configFileUrl != null) {
      if (configFileUrl.getFile().endsWith(".xml")) {
        throw new Exception("not support now!");
      } else if (configFileUrl.getFile().endsWith(".properties")) {
        Properties props = new Properties();
        props.load(
            new InputStreamReader(new FileInputStream(new File(configFileUrl.getFile()))));
        Set<String> keys = props.stringPropertyNames();
        for (String key : keys) {
          if (KAFKA_ZK_QUORUM.equals(key)) {
            config.setBootstrapServers(props.getProperty(key));
          } else if (KAFKA_TOPIC.equals(key)) {
            config.setKafkaTopic(props.getProperty(key));
          } else if (KAFKA_APPID.equals(key)) {
            config.setAppId(props.getProperty(key));
          } else if (key.startsWith(KAFKA_PARAM_PERFIX)) {
            config.getParamMap()
                .put(key.replace(KAFKA_PARAM_PERFIX, ""), props.getProperty(key));
          }
        }

      } else {
        throw new Exception("parse configFile error,not support config file type");
      }
    } else {
      throw new Exception("ERROR! have no slf4j-kafka.properties file in classpath");
    }
    return config;
  }
}
