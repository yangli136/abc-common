package org.abcframework.common.configuration.asyc;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.abcframework.common.configuration.asyc.AsyncTaskExecutorProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
@EnableConfigurationProperties({AsyncTaskExecutorProperties.class})
public class AsyncTaskExecutorConfiguration {

  @Bean
  public Executor threadPoolTaskExecutor(AsyncTaskExecutorProperties taskExecutorConfiguration) {
    ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
    threadPoolTaskExecutor.setMaxPoolSize(taskExecutorConfiguration.getMaxPoolSize());
    threadPoolTaskExecutor.setCorePoolSize(taskExecutorConfiguration.getCorePoolSize());
    threadPoolTaskExecutor.setQueueCapacity(taskExecutorConfiguration.getQueueCapacity());
    threadPoolTaskExecutor.setKeepAliveSeconds(taskExecutorConfiguration.getKeepAliveSeconds());
    threadPoolTaskExecutor.setThreadNamePrefix(taskExecutorConfiguration.getThreadNamePrefix());
    threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    return threadPoolTaskExecutor;
  }
}
