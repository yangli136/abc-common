package org.abcframework.common.aspect.retry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SampleRetryClientService {
  @Autowired private SampleRetryableService sampleRetryService;

  public String retrySuccessful() throws Exception {
    return sampleRetryService.retrySuccessful();
  }

  public String retryWithSimpleExhausted() throws Exception {
    return sampleRetryService.retryWithSimpleExhausted();
  }

  public String retryFailedWithNoneRetryableException() throws Exception {
    return sampleRetryService.retryFailedWithNoneRetryableException();
  }

  public void clear() {
    sampleRetryService.clear();
  }
}
