package org.tron.core.netlog.slf4j_kafka.converter;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import java.net.InetAddress;
import org.tron.core.config.args.Args;


public class NodeIDConverter extends ClassicConverter {

  @Override
  public String convert(ILoggingEvent event) {
    String id;
    String name = "";
    String ip = "";
    String port = "";
    try {
      name = InetAddress.getLocalHost().getHostName();
    } catch (Exception e) {
      name = "unknown";
    }

    try {
      ip = Args.getInstance().getNodeExternalIp();
    } catch (Exception e) {
      ip = "unknown";
    }

    try {
      port = String.valueOf(Args.getInstance().getNodeListenPort());
    } catch (Exception e) {
      port = "unknown";
    }
    id = String.join("::", ip, port, name);
    return id;
  }
}