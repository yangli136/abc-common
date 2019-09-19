package org.abcframework.common.actuator.healthcheck;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
public class AbcCustomHealthIndicator extends AbstractHealthIndicator {

  @Override
  protected void doHealthCheck(Health.Builder builder) throws Exception {
    // Use the builder to build the health status details that should be reported.
    // If you throw an exception, the status will be DOWN with the exception message.

    builder
        .up()
        .withDetail("abc-common", "Alive and Kicking")
        .withDetail("errors", "Nothing! I'm good.");
  }
}
