package com.github.huifer.entity.plugin.core.annotation;

import com.github.huifer.entity.plugin.core.api.EntityConvert;
import com.github.huifer.entity.plugin.core.api.ValidateApi;
import java.lang.annotation.ElementType;

/**
 * entity plugin
 *
 * @author huifer
 */
@java.lang.annotation.Target({ElementType.TYPE})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Documented
@java.lang.annotation.Inherited
public @interface EntityPlugin {

  /**
   * name
   *
   * @return name
   */
  String name();

  /**
   * {@link EntityConvert} class
   *
   * @return class
   */
  Class<? extends EntityConvert> convertClass() default EntityConvert.class;

  /**
   * {@link ValidateApi} class
   *
   * @return class
   */
  Class<? extends ValidateApi> validateApiClass() default ValidateApi.class;

  String cacheKey() default "";

}
