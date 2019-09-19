package org.abcframework.common.configuration.cache;

import static org.assertj.core.api.Assertions.assertThat;

import org.abcframework.common.configuration.CommonBeanConfiguration;
import org.abcframework.common.configuration.cache.CacheNamesCustomizer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@EnableAutoConfiguration
@SpringJUnitConfig(classes = {CommonBeanConfiguration.class, CacheNamesCustomizer.class})
@TestPropertySource(locations = "classpath:common.properties")
public class CacheNamesCustomizerTest {
  @Autowired CacheNamesCustomizer config;

  @Test
  public void getCacheNameList() {
    assertThat(config.getCacheNameList()).isNotEmpty();
    assertThat(config.getCacheNameList().size()).isEqualTo(2);
    assertThat(config.getCacheNameList().get(1)).isEqualTo("default");
    assertThat(config.getCacheNameList().get(0)).isEqualTo("appPropertiesCache");
  }
}
