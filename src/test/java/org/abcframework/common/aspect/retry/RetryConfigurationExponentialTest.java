package org.abcframework.common.aspect.retry;

import org.abcframework.common.configuration.retry.RetryConfiguration;
import org.abcframework.common.validation.TestApplicationContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest
@ContextConfiguration(
    classes = {
      TestApplicationContext.class,
      RetryConfiguration.class,
      SampleRetryClientService.class,
      SampleRetryableService.class
    })
@TestPropertySource(properties = {"retry.policy=exponential"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RetryConfigurationExponentialTest {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(RetryConfigurationExponentialTest.class);

  @Autowired
  @Qualifier("retryTemplateExponential")
  private RetryTemplate retryTemplate;

  @Autowired private SampleRetryClientService client;

  @Test
  public void testRetryTemplate() throws Exception {
    client.clear();
    long start = System.nanoTime();
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
    long timeElapsed = System.nanoTime() - start;
    Assertions.assertThat(timeElapsed).isGreaterThan(1200000000l);
  }
}
