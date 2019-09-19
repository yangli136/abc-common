package org.abcframework.common.statistics.impl;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.abcframework.common.statistics.MeterRegistryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MeterRegistryManagerImpl implements MeterRegistryManager {

  @Autowired private MeterRegistry meterRegistry;

  private static final Logger LOGGER = LoggerFactory.getLogger(MeterRegistryManagerImpl.class);

  @PostConstruct
  private MeterRegistry register() {
    MeterRegistry result = null;
    try {
      meterRegistry.config().commonTags("app", "abc");
      Metrics.addRegistry(meterRegistry);
      LOGGER.debug("A MeterRegistry has been registerd.");
      result = meterRegistry;
    } catch (Exception e) {
      LOGGER.warn("MeterRegistry registration failed!\n", e);
    }
    return result;
  }

  @PreDestroy
  private void unregister() {
    try {
      meterRegistry.close();
      Metrics.removeRegistry(meterRegistry);
      LOGGER.debug("MeterRegistry was unregisterd");
    } catch (Exception e) {
      LOGGER.warn("MeterRegistry unregistration failed!\n", e);
    }
  }

  @Override
  public MeterRegistry getMeterRegistry() {
    return meterRegistry;
  }
}
