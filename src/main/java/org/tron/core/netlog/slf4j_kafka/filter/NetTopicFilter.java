package org.tron.core.netlog.slf4j_kafka.filter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class NetTopicFilter extends Filter<ILoggingEvent> {

  private final String topic = "net";

  @Override
  public FilterReply decide(ILoggingEvent event) {
    if (topic.equals(event.getLoggerName())) {
      return FilterReply.ACCEPT;
    } else {
      return FilterReply.DENY;
    }
  }
}