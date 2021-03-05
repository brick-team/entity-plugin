package com.github.huifer.entity.plugin.core.model;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EntityPluginCacheBean {

  private final Map<Class<?>, String> clazzMapValue = new ConcurrentHashMap<>(64);
  private final Map<String, EntityPluginCache> cacheMap = new ConcurrentHashMap<>(64);

  public Map<String, EntityPluginCache> getCacheMap() {
    return cacheMap;
  }

  public Map<Class<?>, String> getClazzMapValue() {
    return clazzMapValue;
  }

}
