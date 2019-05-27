package org.tron.core.netlog.slf4j_kafka.converter;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import java.net.InetAddress;


public class NodeIDConverter extends ClassicConverter {

  @Override
  public String convert(ILoggingEvent event) {
    String name = "";
    try {
      name = InetAddress.getLocalHost().getHostName();
    } catch (Exception e) {

    }
    return name;
  }
}