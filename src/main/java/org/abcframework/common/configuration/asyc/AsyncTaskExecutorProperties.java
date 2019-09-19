package org.abcframework.common.configuration.asyc;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "abc.task.executor")
@EnableConfigurationProperties
@PropertySource("classpath:taskExecutor.properties")
@Validated
public class AsyncTaskExecutorProperties {
  @Min(0)
  @Max(100)
  private int maxPoolSize;

  @Min(0)
  @Max(100)
  private int corePoolSize;

  @Min(0)
  @Max(100000)
  private int queueCapacity;

  @Min(0)
  @Max(1000)
  private int keepAliveSeconds;

  @NotBlank private String threadNamePrefix;

  private boolean waitForTasksToCompleteOnShutdown;

  @Min(0)
  @Max(1000)
  private int awaitTerminationSeconds;

  public int getMaxPoolSize() {
    return maxPoolSize;
  }

  public void setMaxPoolSize(int maxPoolSize) {
    this.maxPoolSize = maxPoolSize;
  }

  public int getCorePoolSize() {
    return corePoolSize;
  }

  public void setCorePoolSize(int corePoolSize) {
    this.corePoolSize = corePoolSize;
  }

  public int getQueueCapacity() {
    return queueCapacity;
  }

  public void setQueueCapacity(int queueCapacity) {
    this.queueCapacity = queueCapacity;
  }

  public int getKeepAliveSeconds() {
    return keepAliveSeconds;
  }

  public void setKeepAliveSeconds(int keepAliveSeconds) {
    this.keepAliveSeconds = keepAliveSeconds;
  }

  public String getThreadNamePrefix() {
    return threadNamePrefix;
  }

  public void setThreadNamePrefix(String threadNamePrefix) {
    this.threadNamePrefix = threadNamePrefix;
  }

  public boolean isWaitForTasksToCompleteOnShutdown() {
    return waitForTasksToCompleteOnShutdown;
  }

  public void setWaitForTasksToCompleteOnShutdown(boolean waitForTasksToCompleteOnShutdown) {
    this.waitForTasksToCompleteOnShutdown = waitForTasksToCompleteOnShutdown;
  }

  public int getAwaitTerminationSeconds() {
    return awaitTerminationSeconds;
  }

  public void setAwaitTerminationSeconds(int awaitTerminationSeconds) {
    this.awaitTerminationSeconds = awaitTerminationSeconds;
  }

  @Override
  public String toString() {
    return "TaskSchedulerConfiguration [maxPoolSize="
        + maxPoolSize
        + ", corePoolSize="
        + corePoolSize
        + ", queueCapacity="
        + queueCapacity
        + ", keepAliveSeconds="
        + keepAliveSeconds
        + ", threadNamePrefix="
        + threadNamePrefix
        + ", waitForTasksToCompleteOnShutdown="
        + waitForTasksToCompleteOnShutdown
        + ", awaitTerminationSeconds="
        + awaitTerminationSeconds
        + "]";
  }
}
