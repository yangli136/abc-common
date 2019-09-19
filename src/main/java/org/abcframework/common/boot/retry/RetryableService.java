package org.abcframework.common.boot.retry;

import org.abcframework.common.aspect.retry.Retryable;
import org.abcframework.common.exception.RecoverableFailureException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@ConditionalOnProperty(
    name = "abc.enable.retryService",
    havingValue = "true",
    matchIfMissing = false)
@Service
public class RetryableService {

  @Retryable(type = "retryTemplateSimple")
  public void retry() {
    throw new RecoverableFailureException("force a retry.");
  }
}
