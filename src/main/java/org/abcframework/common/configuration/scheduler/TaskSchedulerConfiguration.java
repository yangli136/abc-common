package org.abcframework.common.configuration.scheduler;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "abc.task.scheduler")
@EnableConfigurationProperties
@PropertySource("classpath:taskScheduler.properties")
@Validated
public class TaskSchedulerConfiguration {
  @Min(0)
  @Max(100)
  private int poolSize;

  @NotBlank private String threadNamePrefix;

  public int getPoolSize() {
    return poolSize;
  }

  public void setPoolSize(int poolSize) {
    this.poolSize = poolSize;
  }

  public String getThreadNamePrefix() {
    return threadNamePrefix;
  }

  public void setThreadNamePrefix(String threadNamePrefix) {
    this.threadNamePrefix = threadNamePrefix;
  }

  @Override
  public String toString() {
    return "TaskSchedulerConfiguration [poolSize="
        + poolSize
        + ", threadNamePrefix="
        + threadNamePrefix
        + "]";
  }
}
