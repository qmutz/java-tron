package org.tron.core.netlog.slf4j_kafka.config;

import java.util.HashMap;
import java.util.Map;

public class Slf4jKafkaConfig {
    private String bootstrapServers; //zookeeper Quorum
    private String kafkaTopic;//kafka topic
    private String appId;//appid
    private Map<String,String> paramMap = new HashMap<>();//前缀为kafka.param的均为自定义配置项

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public String getKafkaTopic() {
        return kafkaTopic;
    }

    public void setKafkaTopic(String kafkaTopic) {
        this.kafkaTopic = kafkaTopic;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
