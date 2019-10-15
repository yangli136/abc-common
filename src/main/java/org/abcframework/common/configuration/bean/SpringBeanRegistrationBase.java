package org.abcframework.common.configuration.bean;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;

public class SpringBeanRegistrationBase {
  protected final ApplicationContext applicationContext;

  public SpringBeanRegistrationBase(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  protected BeanDefinitionRegistry getBeanDefinitionRegistry() {
    final AutowireCapableBeanFactory factory = applicationContext.getAutowireCapableBeanFactory();
    return (BeanDefinitionRegistry) factory;
  }
}
