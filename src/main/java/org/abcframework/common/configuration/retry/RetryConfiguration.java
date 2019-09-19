package org.abcframework.common.configuration.retry;

import java.util.HashMap;
import java.util.Map;

import org.abcframework.common.exception.RecoverableFailureException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RetryConfiguration {

  @Bean
  @Primary
  @ConditionalOnExpression("'${retry.policy}' == 'exponential' or '${retry.policy}' == 'all' or ${retry.policy:true}")
  @ConditionalOnProperty(name = "retry.policy", havingValue = "exponential", matchIfMissing = false)
  public RetryTemplate retryTemplateExponential() {
    RetryTemplate retryTemplate = new RetryTemplate();

    ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
    backOffPolicy.setInitialInterval(100l);
    backOffPolicy.setMultiplier(3);
    backOffPolicy.setMaxInterval(600000l); // 10 minutes
    retryTemplate.setBackOffPolicy(backOffPolicy);

    Map<Class<? extends Throwable>, Boolean> retryableExceptions = new HashMap<>();
    retryableExceptions.put(RuntimeException.class, true);

    SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(1000, retryableExceptions);
    retryTemplate.setRetryPolicy(retryPolicy);

    return retryTemplate;
  }

  @Bean
  @Primary
  @ConditionalOnExpression("'${retry.policy}' == 'simple' or '${retry.policy}' == 'all' or ${retry.policy:true}")
  public RetryTemplate retryTemplateSimple() {
    RetryTemplate retryTemplate = new RetryTemplate();

    FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
    backOffPolicy.setBackOffPeriod(100l);
    retryTemplate.setBackOffPolicy(backOffPolicy);

    SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(4, exceptionMap());
    retryTemplate.setRetryPolicy(retryPolicy);

    return retryTemplate;
  }

  @Bean
  @ConditionalOnExpression("'${retry.policy}' == 'none' or '${retry.policy}' == 'all' or ${retry.policy:true}")
  public RetryTemplate retryTemplateNone() {
    RetryTemplate retryTemplate = new RetryTemplate();

    SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(1, exceptionMap());
    retryTemplate.setRetryPolicy(retryPolicy);

    return retryTemplate;
  }

  @Bean
  public Map<Class<? extends Throwable>, Boolean> exceptionMap() {
    final Map<Class<? extends Throwable>, Boolean> retryableExceptions = new HashMap<>();
    retryableExceptions.put(RecoverableFailureException.class, true);
    return retryableExceptions;
  }
}
