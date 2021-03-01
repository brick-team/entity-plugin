package com.github.huifer.entity.plugin.core.beans;

import com.github.huifer.entity.plugin.core.model.EntityPluginCacheBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@ComponentScan("com.github.huifer.entity.plugin.core")
public class EntityPluginCoreBean {

  @Bean
  @Order(value = Ordered.LOWEST_PRECEDENCE - 1)
  public EntityPluginCacheBean entityPluginCacheBean() {
    return new EntityPluginCacheBean();
  }

}
