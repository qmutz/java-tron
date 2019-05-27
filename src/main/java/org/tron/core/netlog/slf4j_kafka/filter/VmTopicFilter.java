package org.tron.core.netlog.slf4j_kafka.filter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class VmTopicFilter extends Filter<ILoggingEvent> {
  private final String topic = "VM";

  @Override
  public FilterReply decide(ILoggingEvent event) {
    if (topic.equals(event.getLoggerName())) {
      return FilterReply.ACCEPT;
    } else {
      return FilterReply.DENY;
    }
  }
}