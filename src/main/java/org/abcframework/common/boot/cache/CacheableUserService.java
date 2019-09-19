package org.abcframework.common.boot.cache;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@CacheConfig(cacheNames = {"default"})
@ConditionalOnProperty(
    name = "abc.enable.asyncCacheableService",
    havingValue = "true",
    matchIfMissing = false)
@Service
public class CacheableUserService {
  private Map<String, String> addressMap = new HashMap<>();

  @Cacheable(key = "#user.id")
  public String getAddress(User user) {
    return addressMap.get(user.getId());
  }

  @CacheEvict(key = "#user.id")
  public void setAddress(User user, String address) {
    this.addressMap.put(user.getId(), address);
  }

  public String getAddressDirectly(User user) {
    return addressMap.get(user.getId());
  }

  public void setAddressDirectly(User user, String address) {
    this.addressMap.put(user.getId(), address);
  }

  @CacheEvict(allEntries = true)
  public void clear() {}
}
