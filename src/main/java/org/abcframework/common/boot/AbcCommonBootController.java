package org.abcframework.common.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@ConditionalOnProperty(name = "web.test.enabled", havingValue = "true", matchIfMissing = false)
@RestController
public class AbcCommonBootController {
  private static final Logger LOGGER = LoggerFactory.getLogger(AbcCommonBootController.class);

  @GetMapping("test")
  public String test() {
    LOGGER.info("test >>>");
    return "OK";
  }
}
