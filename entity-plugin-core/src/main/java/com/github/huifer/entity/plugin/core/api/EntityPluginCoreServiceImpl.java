package com.github.huifer.entity.plugin.core.api;

import com.github.huifer.entity.plugin.core.model.EntityPluginCache;
import com.github.huifer.entity.plugin.core.model.EntityPluginCacheBean;
import com.google.gson.Gson;
import java.lang.reflect.Constructor;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class EntityPluginCoreServiceImpl implements
    EntityPluginCoreService {

  private static final Logger log = LoggerFactory.getLogger(EntityPluginCoreServiceImpl.class);
  Gson gson = new Gson();
  @Autowired
  private ApplicationContext context;
  @Autowired
  private EntityPluginCacheBean entityPluginCacheBean;

  @Override
  public Object findById(String entityPluginName, String id) {
    EntityPluginCache entityPluginCache = entityPluginCacheBean.getCacheMap().get(entityPluginName);

    Class<?> idClass = entityPluginCache.getIdClass();
    Object idData = gson.fromJson(id, idClass);
    CrudRepository crudRepository = entityPluginCache.getCrudRepository();

    Optional byId = crudRepository.findById(idData);

    if (byId.isPresent()) {
      Class<? extends EntityConvert> convertClass = entityPluginCache.getConvertClass();
      if (convertClass == EntityConvert.class) {
        return byId.get();
      }
      // 存在转换类的情况下
      if (convertClass != null) {

        String[] beanNamesForType = context.getBeanNamesForType(convertClass);
        // 在 Spring 中能够搜索到
        if (beanNamesForType.length > 0) {
          String beanName = beanNamesForType[0];
          EntityConvert bean = context.getBean(beanName, convertClass);
          return bean.fromEntity(byId.get());
        }
        // 不能再 Spring 容器中搜索
        else {
          try {
            EntityConvert entityConvert = newInstanceFromEntityConvertClass(
                convertClass);
            return entityConvert.fromEntity(byId.get());

          } catch (Exception e) {
            if (log.isErrorEnabled()) {
              log.error("无参构造初始化失败");
            }
          }
        }
      }
      // 不存在转换类的情况下直接将查询结果返回
      else {
        return byId.get();
      }

    }

    return null;
  }

  private EntityConvert newInstanceFromEntityConvertClass(
      Class<? extends EntityConvert> convertClass)
      throws NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
    // 尝试寻找无参构造
    Constructor<? extends EntityConvert> declaredConstructor = convertClass
        .getDeclaredConstructor();
    EntityConvert entityConvert = declaredConstructor.newInstance();
    return entityConvert;
  }

  @Override
  public Object save(String entityPluginName, Object insertParam) {
    EntityPluginCache entityPluginCache = entityPluginCacheBean.getCacheMap().get(entityPluginName);
    CrudRepository crudRepository = entityPluginCache.getCrudRepository();

    Class<? extends EntityConvert> convertClass = entityPluginCache.getConvertClass();
    if (convertClass == EntityConvert.class) {
      return crudRepository.save(insertParam);
    }
    // 存在转换类的情况下
    if (convertClass != null) {

      String[] beanNamesForType = context.getBeanNamesForType(convertClass);
      // 在 Spring 中能够搜索到
      if (beanNamesForType.length > 0) {
        String beanName = beanNamesForType[0];
        EntityConvert bean = context.getBean(beanName, convertClass);
        // 转换成数据库实体对象
        Object insertDbData = bean.fromInsType(insertParam);
        // 执行插入
        return crudRepository.save(insertDbData);
      }
      // 不能再 Spring 容器中搜索
      else {
        try {
          EntityConvert entityConvert = newInstanceFromEntityConvertClass(
              convertClass);
          Object insertDbData = entityConvert.fromInsType(insertParam);
          return crudRepository.save(insertDbData);
        } catch (Exception e) {
          if (log.isErrorEnabled()) {
            log.error("无参构造初始化失败");
          }
        }
      }
    }
    // 如果不存在转换器类直接进行插入
    else {
      return crudRepository.save(insertParam);
    }

    return null;
  }

  @Override
  public Object update(String entityPluginName, Object updateParam) {
    EntityPluginCache entityPluginCache = entityPluginCacheBean.getCacheMap().get(entityPluginName);
    CrudRepository crudRepository = entityPluginCache.getCrudRepository();

    Class<? extends EntityConvert> convertClass = entityPluginCache.getConvertClass();
    if (convertClass == EntityConvert.class) {
      return crudRepository.save(updateParam);
    }
    if (convertClass != null) {

      String[] beanNamesForType = context.getBeanNamesForType(convertClass);
      // 在 Spring 中能够搜索到
      if (beanNamesForType.length > 0) {
        String beanName = beanNamesForType[0];
        EntityConvert bean = context.getBean(beanName, convertClass);
        // 转换成数据库实体对象
        Object updateDbData = bean.fromUpType(updateParam);
        // 执行插入
        return crudRepository.save(updateDbData);
      }
      // 不能再 Spring 容器中搜索
      else {
        try {
          EntityConvert entityConvert = newInstanceFromEntityConvertClass(
              convertClass);
          Object updateDbData = entityConvert.fromUpType(updateParam);
          return crudRepository.save(updateDbData);

        } catch (Exception e) {
          if (log.isErrorEnabled()) {
            log.error("无参构造初始化失败");
          }
        }
      }
    }

    return null;
  }

  @Override
  public Boolean deleteById(String entityPluginName, String id) {
    EntityPluginCache entityPluginCache = entityPluginCacheBean.getCacheMap().get(entityPluginName);
    Class<?> idClass = entityPluginCache.getIdClass();
    Object o = gson.fromJson(id, idClass);

    CrudRepository crudRepository = entityPluginCache.getCrudRepository();

    crudRepository.deleteById(o);
    Optional byId = crudRepository.findById(o);
    return !byId.isPresent();
  }
}
