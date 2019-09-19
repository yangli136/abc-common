package org.abcframework.common.statistics;

import io.micrometer.core.instrument.MeterRegistry;

public interface MeterRegistryManager {

  MeterRegistry getMeterRegistry();
}
