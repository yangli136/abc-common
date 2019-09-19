package org.abcframework.common.boot.async;

import java.util.concurrent.Future;
import org.abcframework.common.boot.cache.CacheableUserService;
import org.abcframework.common.boot.cache.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

@ConditionalOnProperty(
    name = "abc.enable.asyncCacheableService",
    havingValue = "true",
    matchIfMissing = false)
@Service
public class AsyncCacheableService {
  @Autowired private CacheableUserService cacheableUserService;

  @Async("threadPoolTaskExecutor")
  public Future<String> getAddress(User user) {
    return new AsyncResult<>(this.cacheableUserService.getAddress(user));
  }
}
