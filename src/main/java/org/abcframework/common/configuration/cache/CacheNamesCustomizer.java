package org.abcframework.common.configuration.cache;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.stereotype.Component;

@Component
public class CacheNamesCustomizer implements CacheManagerCustomizer<ConcurrentMapCacheManager> {
  @Value("${cache.name.list}")
  private List<String> cacheNameList;

  public List<String> getCacheNameList() {
    return cacheNameList;
  }

  @Override
  public void customize(ConcurrentMapCacheManager cacheManager) {
    cacheManager.setCacheNames(this.cacheNameList);
  }
}
