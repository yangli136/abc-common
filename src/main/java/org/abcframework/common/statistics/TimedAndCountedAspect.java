package org.abcframework.common.statistics;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import java.util.concurrent.TimeUnit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class TimedAndCountedAspect {
  private static final Logger LOGGER = LoggerFactory.getLogger(TimedAndCountedAspect.class);

  private final MeterRegistry registry;
  private final StatisticsCache staticticsCache;
  private final TimeSampler timeSampler = new TimeSampler();

  public TimedAndCountedAspect(MeterRegistry registry, StatisticsCache statisticsCache) {
    this.registry = registry;
    this.staticticsCache = statisticsCache;
  }

  @Around("execution (@io.micrometer.core.annotation.Timed * *.*(..))")
  public Object timedMethod(ProceedingJoinPoint pjp) throws Throwable {
    final Timer.Sample sample = Timer.start(registry);
    timeSampler.setStart(Clock.SYSTEM.monotonicTime());
    try {
      final Object result = pjp.proceed();
      staticticsCache.getExecutionCounter(pjp, "successCount", registry).increment();
      return result;
    } catch (Exception ex) {
      staticticsCache.getExecutionCounter(pjp, "failCount", registry).increment();
      throw ex;
    } finally {
      staticticsCache.getExecutionCounter(pjp, "totalCount", registry).increment();
      try {
        Timer timer = this.staticticsCache.getExecutionTimer(pjp, registry);
        sample.stop(timer);
      } catch (Exception e) {
        // ignoring on purpose
      }

      timeSampler.setEnd(Clock.SYSTEM.monotonicTime());

      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug(
            "successCount:{}",
            staticticsCache.getExecutionCounter(pjp, "successCount", registry).count());
        LOGGER.debug(
            "failCount:{}",
            staticticsCache.getExecutionCounter(pjp, "failCount", registry).count());
        LOGGER.debug(
            "totalCount:{}",
            staticticsCache.getExecutionCounter(pjp, "totalCount", registry).count());
        LOGGER.debug(
            "timer Count:{}", this.staticticsCache.getExecutionTimer(pjp, registry).count());
        LOGGER.debug(
            "timer totalTime:{}",
            this.staticticsCache.getExecutionTimer(pjp, registry).totalTime(TimeUnit.MILLISECONDS));
        LOGGER.debug(
            "timer max:{}",
            this.staticticsCache.getExecutionTimer(pjp, registry).max(TimeUnit.MILLISECONDS));
        LOGGER.debug(
            "timer mean:{}",
            this.staticticsCache.getExecutionTimer(pjp, registry).mean(TimeUnit.MILLISECONDS));
        LOGGER.debug(
            "gauge eclapsed time:{}",
            this.staticticsCache.getElaspedTimeGauge(pjp, timeSampler, registry).value());
      }
    }
  }
}
