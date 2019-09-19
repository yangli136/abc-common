package org.abcframework.common.boot;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.abcframework.common.boot.async.AsyncCacheableService;
import org.abcframework.common.boot.cache.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@ConditionalOnProperty(
    name = "abc.enable.asyncCacheableService",
    havingValue = "true",
    matchIfMissing = false)
public class AsyncCacheableServiceScheduler {
  private final Logger LOGGER = LoggerFactory.getLogger(AsyncCacheableServiceScheduler.class);

  @Autowired private AsyncCacheableService asyncCacheableService;

  @Scheduled(cron = "0 * * * * ?")
  public void getAddress() {
    User user = new User(AbcCommonBootController.DEFAULT_USERID);
    Future<String> result = asyncCacheableService.getAddress(user);
    try {
      LOGGER.info("User({}) has address:{}", user.getId(), result.get());
    } catch (InterruptedException | ExecutionException e) {
      LOGGER.error("Failed to retrieve address.", e);
    }
  }
}
