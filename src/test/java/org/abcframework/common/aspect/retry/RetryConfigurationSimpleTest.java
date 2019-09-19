package org.abcframework.common.aspect.retry;

import java.time.Clock;
import java.time.Instant;
import org.abcframework.common.configuration.retry.RetryConfiguration;
import org.abcframework.common.validation.TestApplicationContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest(
    classes = {
      TestApplicationContext.class,
      RetryConfiguration.class,
      SampleRetryClientService.class,
      SampleRetryableService.class
    })
@TestPropertySource(properties = {"retry.policy=simple"})
public class RetryConfigurationSimpleTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(RetryConfigurationSimpleTest.class);

  @Autowired private RetryTemplate retryTemplate;
  @Autowired private SampleRetryClientService client;
  private Instant instant = Clock.systemDefaultZone().instant();

  @Test
  public void testRetryTemplate() throws Exception {

    long start = instant.getNano();
    retryTemplate.execute(
        context -> {
          final String message = client.retrySuccessful();
          Assertions.assertThat(message).isEqualTo("3");
          return message;
        },
        context -> {
          Throwable ex = context.getLastThrowable();
          if (ex != null) {
            LOGGER.info(
                "Recovery callback called with exception:{}", ex.getClass().getSimpleName());
          } else {
            LOGGER.info("Recovery callback called without exception.");
          }
          return ex;
        });
    long timeElapsed = instant.getNano() - start;
    Assertions.assertThat(timeElapsed).isLessThan(400000000l);
  }
}
