package org.abcframework.common.boot;

import org.abcframework.common.boot.cache.CacheableUserService;
import org.abcframework.common.boot.cache.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@ConditionalOnProperty(name = "web.test.enabled", havingValue = "true", matchIfMissing = false)
@RestController
public class AbcCommonBootController {
  public static final String DEFAULT_USERID = "u1";
  private static final Logger LOGGER = LoggerFactory.getLogger(AbcCommonBootController.class);

  @Autowired private CacheableUserService cacheableUserService;

  @GetMapping("test")
  public String test() {
    LOGGER.info("test >>>");
    return "OK";
  }

  @GetMapping("setAddress")
  public String send(
      @RequestParam(required = false) String userId,
      @RequestParam(required = true) String address) {
    LOGGER.info("userId:{}", userId);
    if (userId == null) {
      userId = DEFAULT_USERID;
    }
    LOGGER.info("setting address:{} for userId:{} >>>", address, userId);
    final User user = new User(userId);
    cacheableUserService.setAddress(user, address);
    return "OK";
  }

  @GetMapping("getAddress")
  public String send(@RequestParam(required = true) String userId) {
    LOGGER.info("getting address for userId:{} >>>", userId);
    final User user = new User(userId);
    return cacheableUserService.getAddress(user);
  }
}
