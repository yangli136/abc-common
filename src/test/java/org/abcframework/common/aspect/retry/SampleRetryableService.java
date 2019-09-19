package org.abcframework.common.aspect.retry;

import org.abcframework.common.exception.RecoverableFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SampleRetryableService {
  private static final Logger LOGGER = LoggerFactory.getLogger(SampleRetryableService.class);
  private static int COUNTER = 0;

  @Retryable(type = "retryTemplateSimple")
  public String retrySuccessful() throws Exception {
    COUNTER++;
    LOGGER.info("COUNTER = " + COUNTER);
    if (COUNTER <= 3) {
      throw new RecoverableFailureException("test - RuntimeException.");
    } else {
      return String.valueOf(COUNTER);
    }
  }

  @Retryable(type = "retryTemplateSimple")
  public String retryWithSimpleExhausted() throws Exception {
    COUNTER++;
    LOGGER.info("COUNTER = " + COUNTER);
    if (COUNTER <= 6) {
      throw new RecoverableFailureException("test - RuntimeException.");
    } else {
      return "completed.";
    }
  }

  @Retryable(type = "retryTemplateSimple")
  public String retryFailedWithNoneRetryableException() throws Exception {
    COUNTER++;
    LOGGER.info("COUNTER = " + COUNTER);
    if (COUNTER <= 2) {
      throw new RecoverableFailureException("test - RuntimeException.");
    } else {
      throw new IllegalStateException();
    }
  }

  public void clear() {
    COUNTER = 0;
  }
}
