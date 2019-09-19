package org.abcframework.common.aspect.retry;

import org.abcframework.common.exception.AbcApplicationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@Aspect
public class RetryableAspect {
  private static final Logger LOGGER = LoggerFactory.getLogger(RetryableAspect.class);

  @Autowired private ApplicationContext applicationContext;

  @Pointcut("execution(* *(..)) && @annotation(org.abcframework.common.aspect.retry.Retryable)")
  public void methodRetryable() {}

  @Around(
      "org.abcframework.common.aspect.retry.RetryableAspect.methodRetryable() && @annotation(retryable)")
  public Object aroundRetryableMethod(ProceedingJoinPoint pjp, Retryable retryable)
      throws Throwable {
    final RetryTemplate retryTemplate = getRetryTemplate(retryable);
    return retryTemplate.execute(
        context -> {
          return pjp.proceed();
        },
        context -> {
          Throwable ex = context.getLastThrowable();
          int retryCount = context.getRetryCount();
          if (ex != null) {
            if (null != ex.getCause()) {
              LOGGER.debug("LastThrowable:{}", ex.getCause().getClass().getSimpleName());
            } else {
              LOGGER.debug("LastThrowable:{}", ex.getClass().getSimpleName());
            }
            throw new AbcApplicationException(
                "Retried " + retryCount + " times. failed with exception.", ex);
          } else {
            throw new AbcApplicationException(
                "Retried " + retryCount + " times. Ended without exception.");
          }
        });
  }

  private RetryTemplate getRetryTemplate(Retryable retryable) {
    final String retryableTemplateType = retryable.type();
    final RetryTemplate retryTemplate =
        (RetryTemplate) applicationContext.getBean(retryableTemplateType);
    return retryTemplate;
  }
}
