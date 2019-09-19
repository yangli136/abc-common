package org.abcframework.common.statistics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import javax.validation.constraints.NotNull;
import org.aspectj.lang.ProceedingJoinPoint;

public interface StatisticsCache {

  Counter getExecutionCounter(
      @NotNull ProceedingJoinPoint joinPoint,
      @NotNull String counterType,
      @NotNull MeterRegistry meterRegistry);

  Timer getExecutionTimer(
      @NotNull ProceedingJoinPoint joinPoint, @NotNull @NotNull MeterRegistry meterRegistry);

  Gauge getElaspedTimeGauge(
      @NotNull ProceedingJoinPoint joinPoint,
      @NotNull TimeSampler timeSampler,
      @NotNull MeterRegistry meterRegistry);

  void clear();
}
