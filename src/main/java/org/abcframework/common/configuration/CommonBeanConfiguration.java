package org.abcframework.common.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

@Configuration
@EnableCaching
public class CommonBeanConfiguration {

  /**
   * This activates a configuration service which supports converting String to Collection types. If
   * you do not activate this configuration service, Spring falls back on its legacy property
   * editors as configuration services, which do not support this kind of conversion.
   */
  @Bean
  public ConversionService conversionService() {
    return new DefaultConversionService();
  }
}
