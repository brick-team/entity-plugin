package com.github.huifer.entity.plugin.core.runner;

import com.github.huifer.entity.plugin.core.annotation.EntityPlugin;
import com.github.huifer.entity.plugin.core.api.EntityConvert;
import com.github.huifer.entity.plugin.core.model.EntityPluginCache;
import com.github.huifer.entity.plugin.core.model.EntityPluginCacheBean;
import com.github.huifer.entity.plugin.core.utils.InterfaceReflectUtils;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

@Component
public class EntityPluginRunner implements ApplicationRunner, ApplicationContextAware, Ordered {

  private static final Logger log = LoggerFactory.getLogger(EntityPluginRunner.class);
  @Autowired
  private ApplicationContext context;
  @Autowired
  private EntityPluginCacheBean entityPluginCacheBean;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    Map<String, CrudRepository> crudRepositoryMap
        = context.getBeansOfType(CrudRepository.class);

    crudRepositoryMap.forEach((k, v) -> {
      Class<?>[] repositoryInterfaces = AopProxyUtils.proxiedUserInterfaces(v);
      for (Class<?> repositoryInterface : repositoryInterfaces) {
        List<Class<?>> interfaceGenericLasses = InterfaceReflectUtils
            .getInterfaceGenericLasses(repositoryInterface,
                Repository.class);
        if (!CollectionUtils.isEmpty(interfaceGenericLasses)) {
          // entity class
          Class<?> entityClass = interfaceGenericLasses.get(0);
          EntityPlugin annotation = entityClass.getAnnotation(EntityPlugin.class);
          if (annotation != null) {

            Map<String, EntityPluginCache> cacheMap = entityPluginCacheBean.getCacheMap();
            EntityPluginCache value = new EntityPluginCache();
            value.setName(annotation.name());
            value.setSelf(entityClass);
            value.setIdClass(interfaceGenericLasses.get(1));
            value.setConvertClass(annotation.convertClass());
            value.setCrudRepository(v);

            try {
              handlerEntityConvert(annotation.convertClass(), value);
            } catch (Exception e) {
              e.printStackTrace();
            }

            if (cacheMap.containsKey(annotation.name())) {
              try {
                if (log.isErrorEnabled()) {
                  log.error("不允许出现相同的EntityPlugin名称 ,entity = [{}]", entityClass);
                }
                throw new Exception("不允许出现相同的EntityPlugin名称");
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
            cacheMap.put(annotation.name(), value);
          }
        }
      }

    });
    System.out.println();
  }

  private void handlerEntityConvert(Class<? extends EntityConvert> convertClass,
      EntityPluginCache value) throws Exception {
    Type[] genericInterfaces = convertClass.getGenericInterfaces();

    for (Type genericInterface : genericInterfaces) {
      if (genericInterface instanceof ParameterizedTypeImpl) {
        boolean assignableFrom = EntityConvert.class
            .isAssignableFrom(((ParameterizedTypeImpl) genericInterface).getRawType());
        if (assignableFrom) {
          Type[] actualTypeArguments = ((ParameterizedTypeImpl) genericInterface)
              .getActualTypeArguments();

          if (actualTypeArguments.length == 4) {

            Type nsType = actualTypeArguments[0];
            Type upType = actualTypeArguments[1];
            Type resType = actualTypeArguments[2];

            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            Class<?> nsTypeClass = contextClassLoader
                .loadClass(nsType.getTypeName());
            Class<?> upTypeClass = contextClassLoader
                .loadClass(resType.getTypeName());
            Class<?> resTypeClass = contextClassLoader
                .loadClass(resType.getTypeName());

            value.setInsType(nsTypeClass);
            value.setUpType(upTypeClass);
            value.setResType(resTypeClass);
          }


        }
      }
    }
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.context = applicationContext;
  }

  @Override
  public int getOrder() {
    return Ordered.LOWEST_PRECEDENCE;
  }
}
