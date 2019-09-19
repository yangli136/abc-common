package org.abcframework.common.boot.retry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@ConditionalOnProperty(
    name = "abc.enable.retryService",
    havingValue = "true",
    matchIfMissing = false)
@RestController
public class AbcCommonRetryController {

  @Autowired private RetryableService retryableService;

  @GetMapping("retry")
  public String retry() {
    retryableService.retry();
    return "OK";
  }
}
