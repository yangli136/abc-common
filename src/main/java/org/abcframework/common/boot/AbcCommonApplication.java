package org.abcframework.common.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan(
    basePackages = {"org.abcframework.common"},
    excludeFilters =
        @ComponentScan.Filter(
            type = FilterType.REGEX,
            pattern = "org\\.abcframework\\..\\.demo..*"))
@EnableAspectJAutoProxy
@EnableAsync
@EnableCaching
@EnableScheduling
@PropertySource("classpath:common.properties")
@SpringBootApplication
public class AbcCommonApplication {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(AbcCommonApplication.class, args);
    //    TimeUnit.HOURS.sleep(1);
  }
}
