package org.tron.core.netlog.slf4j_kafka.config;

public class Slf4jKafkaContext {
    private static Slf4jKafkaConfig slf4jKafkaConfig;

    public static Slf4jKafkaConfig getContextSlf4jKafkaConfig() throws Exception {
        if(slf4jKafkaConfig == null) {
            slf4jKafkaConfig = Slf4jKafkaConfigSimpleFactory.getInstance().parse();
        }
        return slf4jKafkaConfig;
    }
}
