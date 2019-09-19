package org.abcframework.common.configuration.async;

import static org.assertj.core.api.Assertions.assertThat;

import org.abcframework.common.configuration.asyc.AsyncTaskExecutorProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@EnableAutoConfiguration
@SpringJUnitConfig(classes = {AsyncTaskExecutorProperties.class})
public class TaskExecutorConfigurationTest {
  @Autowired private AsyncTaskExecutorProperties config;

  @Test
  public void getCacheNameList() {
    assertThat(config.getMaxPoolSize()).isEqualTo(10);
    assertThat(config.getThreadNamePrefix()).isEqualTo("abc.threadPoolTaskExecutor");
  }
}
