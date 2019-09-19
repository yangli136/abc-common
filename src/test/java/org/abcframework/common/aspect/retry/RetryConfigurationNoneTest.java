package org.abcframework.common.aspect.retry;

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
@TestPropertySource(properties = {"retry.policy=none"})
public class RetryConfigurationNoneTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(RetryConfigurationNoneTest.class);

  @Autowired private RetryTemplate retryTemplate;
  @Autowired private SampleRetryClientService client;

  @Test
  public void testRetryTemplate() throws Exception {

    retryTemplate.execute(
        context -> {
          final String message = client.retrySuccessful();
          Assertions.assertThat(message).isEqualTo("1");
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
  }
}
