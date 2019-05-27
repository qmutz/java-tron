package org.tron.core.netlog.slf4j_kafka.layout;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import org.tron.core.netlog.slf4j_kafka.converter.EncodedMessageConverter;
import org.tron.core.netlog.slf4j_kafka.converter.NodeIDConverter;
import org.tron.core.netlog.slf4j_kafka.converter.TimestampConvert;

public class ExtendedPatternLayoutEncoder extends PatternLayoutEncoder {
  @Override
  public void start() {
    // put your converter
    PatternLayout.defaultConverterMap.put(
        "nodeid", NodeIDConverter.class.getName());
    PatternLayout.defaultConverterMap.put(
        "raw", EncodedMessageConverter.class.getName());
    PatternLayout.defaultConverterMap.put(
        "timestamp", TimestampConvert.class.getName());
    super.start();
  }
}