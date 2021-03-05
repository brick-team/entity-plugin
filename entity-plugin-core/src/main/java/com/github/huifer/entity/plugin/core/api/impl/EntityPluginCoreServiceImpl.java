package com.github.huifer.entity.plugin.core.api.impl;

import com.github.huifer.entity.plugin.core.api.BeanFindService;
import com.github.huifer.entity.plugin.core.api.EntityConvert;
import com.github.huifer.entity.plugin.core.api.EntityPluginCoreService;
import com.github.huifer.entity.plugin.core.api.ValidateApi;
import com.github.huifer.entity.plugin.core.model.EntityPluginCache;
import com.github.huifer.entity.plugin.core.model.EntityPluginCacheBean;
import com.google.gson.Gson;
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

  @Autowired
  private BeanFindService beanFindService;

  @Override
  public Object findById(String entityPluginName, String id) throws Exception {
    if (log.isInfoEnabled()) {
      log.info("findById,entityPluginName = {}, id = {}", entityPluginName, id);
    }
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
        EntityConvert entityConvert1 = beanFindService.findEntityConvert(convertClass);
        if (entityConvert1 != null) {
          return entityConvert1.fromEntity(byId.get());
        }
      }
      // 不存在转换类的情况下直接将查询结果返回
      else {
        return byId.get();
      }

    }

    return null;
  }


  @Override
  public Object save(String entityPluginName, Object insertParam) throws Exception {
    if (log.isInfoEnabled()) {
      log.info("save,entityPluginName = {}, insertParam = {}", entityPluginName,
          gson.toJson(insertParam));
    }
    EntityPluginCache entityPluginCache = entityPluginCacheBean.getCacheMap().get(entityPluginName);
    CrudRepository crudRepository = entityPluginCache.getCrudRepository();

    Class<? extends EntityConvert> convertClass = entityPluginCache.getConvertClass();
    if (convertClass == EntityConvert.class) {

      validateInsert(insertParam, entityPluginCache);

      return crudRepository.save(insertParam);
    }
    // 存在转换类的情况下
    if (convertClass != null) {
      Object insertData = gson
          .fromJson(gson.toJson(insertParam), entityPluginCache.getConvertTypeParam().getInsType());
      validateInsert(insertData, entityPluginCache);

      EntityConvert entityConvert1 = beanFindService.findEntityConvert(convertClass);
      Object insertDbData = entityConvert1
          .fromInsType(insertData);
      return crudRepository.save(insertDbData);
    }
    // 如果不存在转换器类直接进行插入
    else {
      validateInsert(insertParam, entityPluginCache);
      return crudRepository.save(insertParam);
    }
  }

  private void validateInsert(Object insertParam, EntityPluginCache entityPluginCache)
      throws Exception {
    Class<? extends ValidateApi> validateApiClass = entityPluginCache.getValidateApiClass();
    if (validateApiClass != ValidateApi.class) {
      ValidateApi validateApi = beanFindService.findValidateApi(validateApiClass);
      if (validateApi != null) {
        validateApi.validateInsType(insertParam);
      }
    }
  }

  @Override
  public Object update(String entityPluginName, Object updateParam) throws Exception {
    if (log.isInfoEnabled()) {
      log.info("update,entityPluginName = {}, updateParam = {}", entityPluginName,
          gson.toJson(updateParam));
    }
    EntityPluginCache entityPluginCache = entityPluginCacheBean.getCacheMap().get(entityPluginName);
    CrudRepository crudRepository = entityPluginCache.getCrudRepository();

    Class<? extends EntityConvert> convertClass = entityPluginCache.getConvertClass();
    if (convertClass == EntityConvert.class) {
      return crudRepository.save(updateParam);
    }
    if (convertClass != null) {
      Object updateData = gson.fromJson(gson.toJson(updateParam),
          entityPluginCache.getConvertTypeParam().getUpType());
      validateUpdate(entityPluginCache, updateData);
      EntityConvert entityConvert1 = beanFindService.findEntityConvert(convertClass);
      Object updateDbData = entityConvert1
          .fromUpType(updateData);
      return crudRepository.save(updateDbData);
    } else {
      validateUpdate(entityPluginCache, updateParam);
      return crudRepository.save(updateParam);
    }

  }

  private void validateUpdate(EntityPluginCache entityPluginCache, Object updateData)
      throws Exception {
    Class<? extends ValidateApi> validateApiClass = entityPluginCache.getValidateApiClass();
    if (validateApiClass != ValidateApi.class) {
      ValidateApi validateApi = beanFindService.findValidateApi(validateApiClass);
      if (validateApi != null) {
        validateApi.validateUpType(updateData);
      }
    }
  }

  @Override
  public Boolean deleteById(String entityPluginName, String id) {
    if (log.isInfoEnabled()) {
      log.info("deleteById,entityPluginName = {}, id = {}", entityPluginName, id);
    }
    EntityPluginCache entityPluginCache = entityPluginCacheBean.getCacheMap().get(entityPluginName);
    Class<?> idClass = entityPluginCache.getIdClass();
    Object o = gson.fromJson(id, idClass);

    CrudRepository crudRepository = entityPluginCache.getCrudRepository();

    crudRepository.deleteById(o);
    Optional byId = crudRepository.findById(o);
    return !byId.isPresent();
  }
}
