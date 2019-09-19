package org.abcframework.common.statistics.impl;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Map;
import java.util.function.Function;
import javax.validation.constraints.NotNull;
import org.abcframework.common.statistics.StatisticsCache;
import org.abcframework.common.statistics.TimeSampler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class StatisticsCacheImpl implements StatisticsCache {
  private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsCacheImpl.class);
  private Map<String, Counter> counterCache = new ConcurrentReferenceHashMap<>();
  private Map<String, Gauge> gaugeCache = new ConcurrentReferenceHashMap<>();
  private Map<String, Timer> timerCache = new ConcurrentReferenceHashMap<>();

  @Override
  public Counter getExecutionCounter(
      @NotNull ProceedingJoinPoint pjp,
      @NotNull String counterType,
      @NotNull MeterRegistry meterRegistry) {
    final Timed timed = this.getTimed(pjp);
    final Function<ProceedingJoinPoint, Iterable<Tag>> tagsBasedOnJoinPoint =
        this.getTagsReteivingFunction();
    return counterCache.computeIfAbsent(
        getKey(pjp, counterType),
        key ->
            Counter.builder(key)
                .description(timed.description().isEmpty() ? null : timed.description())
                .tags(timed.extraTags())
                .tags(tagsBasedOnJoinPoint.apply(pjp))
                .register(meterRegistry));
  }

  @Override
  public Timer getExecutionTimer(
      @NotNull ProceedingJoinPoint pjp, @NotNull @NotNull MeterRegistry meterRegistry) {
    final Timed timed = getTimed(pjp);
    final Function<ProceedingJoinPoint, Iterable<Tag>> tagsBasedOnJoinPoint =
        this.getTagsReteivingFunction();
    return timerCache.computeIfAbsent(
        getKey(pjp, "timer"),
        key ->
            Timer.builder(key)
                .description(timed.description().isEmpty() ? null : timed.description())
                .tags(timed.extraTags())
                .tags(tagsBasedOnJoinPoint.apply(pjp))
                .publishPercentileHistogram(timed.histogram())
                .publishPercentiles(timed.percentiles().length == 0 ? null : timed.percentiles())
                .distributionStatisticExpiry(Duration.ofHours(1))
                .register(meterRegistry));
  }

  private Timed getTimed(ProceedingJoinPoint pjp) {
    Method method = ((MethodSignature) pjp.getSignature()).getMethod();
    Timed timed = method.getAnnotation(Timed.class);
    if (timed == null) {
      try {
        method = pjp.getTarget().getClass().getMethod(method.getName(), method.getParameterTypes());
      } catch (NoSuchMethodException | SecurityException e) {
        LOGGER.warn("failed to retrieve method information.", e);
      }
      timed = method.getAnnotation(Timed.class);

      if (timed == null) {
        timed =
            new Timed() {
              @Override
              public Class<? extends Annotation> annotationType() {
                return Timed.class;
              }

              @Override
              public String value() {
                return null;
              }

              @Override
              public String[] extraTags() {
                return null;
              }

              @Override
              public boolean longTask() {
                return false;
              }

              @Override
              public double[] percentiles() {
                return null;
              }

              @Override
              public boolean histogram() {
                return false;
              }

              @Override
              public String description() {
                return null;
              }
            };
      }
    }
    return timed;
  }

  private Function<ProceedingJoinPoint, Iterable<Tag>> getTagsReteivingFunction() {
    return pjp ->
        Tags.of(
            "class",
            pjp.getStaticPart().getSignature().getDeclaringTypeName(),
            "method",
            pjp.getStaticPart().getSignature().getName());
  }

  @Override
  public Gauge getElaspedTimeGauge(
      @NotNull ProceedingJoinPoint pjp,
      @NotNull TimeSampler timeSampler,
      @NotNull MeterRegistry meterRegistry) {
    final Timed timed = getTimed(pjp);
    final Function<ProceedingJoinPoint, Iterable<Tag>> tagsBasedOnJoinPoint =
        this.getTagsReteivingFunction();
    return gaugeCache.computeIfAbsent(
        getKey(pjp, "gauge"),
        key ->
            Gauge.builder(key, timeSampler::calculateEclapsedTime)
                .description(timed.description().isEmpty() ? null : timed.description())
                .tags(timed.extraTags())
                .tags(tagsBasedOnJoinPoint.apply(pjp))
                .register(meterRegistry));
  }

  private String getKey(ProceedingJoinPoint pjp, String suffix) {
    final String className = pjp.getStaticPart().getSignature().getDeclaringTypeName();
    final String methodName = pjp.getStaticPart().getSignature().getName();
    return className + "." + methodName + "." + suffix;
  }

  @Override
  public void clear() {
    this.counterCache.forEach((k, v) -> v.close());
    this.counterCache.clear();
    this.gaugeCache.forEach((k, v) -> v.close());
    this.gaugeCache.clear();
    this.timerCache.forEach((k, v) -> v.close());
    this.timerCache.clear();
  }
}
