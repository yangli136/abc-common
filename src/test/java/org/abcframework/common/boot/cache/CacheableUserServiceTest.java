package org.abcframework.common.boot.cache;

import static org.assertj.core.api.Assertions.assertThat;

import org.abcframework.common.configuration.CommonBeanConfiguration;
import org.abcframework.common.configuration.cache.CacheNamesCustomizer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@EnableAutoConfiguration
@SpringBootTest(properties = {"abc.enable.asyncCacheableService=true"})
@SpringJUnitConfig(
    classes = {
      CommonBeanConfiguration.class,
      CacheableUserService.class,
      CacheNamesCustomizer.class
    })
@TestPropertySource(locations = "classpath:common.properties")
public class CacheableUserServiceTest {
  @Autowired private CacheableUserService userService;

  @Test
  public void getCacheNameList() {
    User user1 = new User("user1");
    String address0 = userService.getAddress(user1);
    assertThat(address0).isNull();

    String address1 = "address1";
    userService.setAddress(user1, address1);
    String addressCached = userService.getAddress(user1);
    assertThat(addressCached).isEqualTo(address1);

    String address2 = "address2";
    userService.setAddressDirectly(user1, address2);
    String addressDirect = userService.getAddressDirectly(user1);
    assertThat(addressDirect).isEqualTo(address2);
    addressCached = userService.getAddress(user1);
    assertThat(addressCached).isEqualTo(address1);

    String address3 = "address3";
    userService.setAddress(user1, address3);
    String addressAfterPut = userService.getAddress(user1);
    assertThat(addressAfterPut).isEqualTo(address3);

    String address4 = "address4";
    userService.setAddressDirectly(user1, address4);
    String addressAfterDirect = userService.getAddress(user1);
    assertThat(addressAfterDirect).isEqualTo(address3);
    userService.clear();
    String addressEvicted = userService.getAddress(user1);
    assertThat(addressEvicted).isEqualTo(address4);
  }
}
