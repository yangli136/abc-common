package org.abcframework.common.configuration.scheduler;

import static org.assertj.core.api.Assertions.assertThat;

import org.abcframework.common.configuration.scheduler.TaskSchedulerConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@EnableAutoConfiguration
@SpringJUnitConfig(classes = {TaskSchedulerConfiguration.class})
public class TaskSchedulerConfigurationTest {
  @Autowired private TaskSchedulerConfiguration config;

  @Test
  public void getCacheNameList() {
    assertThat(config.getPoolSize()).isEqualTo(2);
    assertThat(config.getThreadNamePrefix()).isEqualTo("abc.threadPoolTaskScheduler");
  }
}
