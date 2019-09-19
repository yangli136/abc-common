package org.abcframework.common.boot.statistics;

import io.micrometer.core.annotation.Timed;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@ConditionalOnProperty(
    name = "abc.enable.delayedService",
    havingValue = "true",
    matchIfMissing = false)
@Service
@Validated
public class DelayedService {
  private static final Logger LOGGER = LoggerFactory.getLogger(DelayedService.class);

  @Timed(
      description = "DelayedService",
      extraTags = {"DelayedService-tag1", "DelayedService-tag2"},
      histogram = true,
      percentiles = {0.5, 0.9, 0.95, 0.99})
  public void doWithDelay() throws InterruptedException {
    LOGGER.info("doWithDelay...");
    TimeUnit.SECONDS.sleep(1);
  }
}
