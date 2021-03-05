package com.github.huifer.entity.plugin.core.api.impl;

import com.github.huifer.entity.plugin.core.api.EntityPluginCoreService;
import com.github.huifer.entity.plugin.core.api.EntityUseService;
import com.github.huifer.entity.plugin.core.api.ID;
import com.github.huifer.entity.plugin.core.beans.EntityPluginCoreBean;
import com.github.huifer.entity.plugin.core.model.EntityPluginCache;
import com.google.gson.Gson;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class EntityUseServiceImpl implements
    EntityUseService {

  @Autowired
  Gson gson;
  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  @Autowired
  private EntityPluginCoreBean coreBean;
  @Autowired
  private EntityPluginCoreService entityPluginCoreService;

  @Override
  public <T> T findById(String id, Class<T> clazz) {
    String entityName = getEntityName(clazz);
    try {
      String cacheKey = getCacheKey(entityName);
      if (StringUtils.hasLength(cacheKey)) {
        String redisData = (String) stringRedisTemplate.opsForHash().get(cacheKey, id);
        if (StringUtils.hasLength(redisData)) {
          return gson.fromJson(redisData, clazz);
        } else {
          Object byId = entityPluginCoreService.findById(entityName, id);
          stringRedisTemplate.opsForHash().put(cacheKey, id, gson.toJson(byId));
          return (T) byId;
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private String getCacheKey(String entityName) {
    EntityPluginCache entityPluginCache = coreBean.entityPluginCacheBean().getCacheMap()
        .get(entityName);
    return entityPluginCache.getCacheKey();

  }

  @Override
  public <T> T save(Object insertParam, Class<T> clazz) {
    String entityName = getEntityName(clazz);
    try {
      String cacheKey = getCacheKey(entityName);
      if (StringUtils.hasLength(cacheKey)) {
        // save db
        Object data = entityPluginCoreService.save(entityName, insertParam);

        String id = null;
        if (data instanceof ID) {
          Object id1 = ((ID) data).getId();
          id = String.valueOf(id1);
        }
        // save redis
        this.stringRedisTemplate.opsForHash().put(cacheKey, String.valueOf(id), gson.toJson(data));
        return (T) data;
      }
      return (T) entityPluginCoreService.save(entityName, insertParam);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public <T> T update(Object updateParam, Class<T> clazz) {
    String entityName = getEntityName(clazz);
    try {

      String cacheKey = getCacheKey(entityName);
      if (StringUtils.hasLength(cacheKey)) {
        // 删除缓存
        if (updateParam instanceof ID) {
          Object id1 = ((ID) updateParam).getId();
          String id = String.valueOf(id1);
          this.stringRedisTemplate.opsForHash().delete(cacheKey, id);
        }

        // 更新数据库
        T t = (T) entityPluginCoreService.update(entityName, updateParam);
        // 更新缓存
        if (t instanceof ID) {
          Object id = ((ID) t).getId();
          this.stringRedisTemplate.opsForHash().put(cacheKey, String.valueOf(id), gson.toJson(t));
        }
      }
      return (T) entityPluginCoreService.update(entityName, updateParam);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public Boolean deleteById(String id, Class<?> clazz) {
    String entityName = getEntityName(clazz);
    return entityPluginCoreService.deleteById(entityName, id);
  }

  private String getEntityName(Class<?> clazz) {
    Map<Class<?>, String> clazzMapValue = coreBean.entityPluginCacheBean().getClazzMapValue();
    return clazzMapValue.get(clazz);
  }
}
