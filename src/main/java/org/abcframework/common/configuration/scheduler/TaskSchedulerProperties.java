package org.abcframework.common.configuration.scheduler;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableAsync
@EnableConfigurationProperties({TaskSchedulerConfiguration.class})
public class TaskSchedulerProperties {

  @Bean
  public ThreadPoolTaskScheduler threadPoolTaskScheduler(
      TaskSchedulerConfiguration taskSchedulerConfiguration) {
    ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
    threadPoolTaskScheduler.setPoolSize(taskSchedulerConfiguration.getPoolSize());
    threadPoolTaskScheduler.setThreadNamePrefix("abc.threadPoolTaskScheduler");
    return threadPoolTaskScheduler;
  }
}
