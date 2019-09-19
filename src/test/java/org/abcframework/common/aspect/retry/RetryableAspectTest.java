package org.abcframework.common.aspect.retry;

import java.time.Clock;
import java.time.Instant;
import org.abcframework.common.configuration.retry.RetryConfiguration;
import org.abcframework.common.exception.AbcApplicationException;
import org.abcframework.common.exception.RecoverableFailureException;
import org.abcframework.common.validation.TestApplicationContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
    classes = {
      TestApplicationContext.class,
      RetryConfiguration.class,
      SampleRetryClientService.class,
      SampleRetryableService.class,
      RetryableAspect.class
    })
@EnableAspectJAutoProxy
@TestPropertySource(properties = {"retry.policy=simple"})
public class RetryableAspectTest {

  @Autowired private SampleRetryClientService client;

  private Instant instant = Clock.systemDefaultZone().instant();

  @BeforeEach
  private void setup() {
    client.clear();
  }

  @Test
  public void retryWithSimpleExhausted() throws Exception {
    long start = instant.getNano();
    Assertions.assertThatThrownBy(() -> client.retryWithSimpleExhausted())
        .isInstanceOf(AbcApplicationException.class)
        .hasCauseInstanceOf(RecoverableFailureException.class);
    long duration = instant.getNano() - start;
    System.out.println("duration:" + duration);
  }

  @Test
  public void retrySuccessful() throws Exception {
    Assertions.assertThat(client.retrySuccessful()).isEqualTo("4");
  }

  @Test
  public void retryFailedWithNoneRetryableException() throws Exception {
    Assertions.assertThatThrownBy(() -> client.retryFailedWithNoneRetryableException())
        .isInstanceOf(AbcApplicationException.class)
        .hasCauseInstanceOf(IllegalStateException.class);
  }
}
