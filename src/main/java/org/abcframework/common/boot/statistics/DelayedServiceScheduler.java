package org.abcframework.common.boot.statistics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@ConditionalOnProperty(
    name = "abc.enable.delayedService",
    havingValue = "true",
    matchIfMissing = false)
@Service
public class DelayedServiceScheduler {
  private static final Logger LOGGER = LoggerFactory.getLogger(DelayedServiceScheduler.class);

  @Autowired private DelayedService service;

  @Scheduled(fixedDelay = 10000)
  public void runService() {
    try {
      this.service.doWithDelay();
    } catch (InterruptedException e) {
      LOGGER.info("doWithDelay failed with exception.", e);
    }
  }
}
