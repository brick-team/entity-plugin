package com.github.huifer.entity.plugin.core.model;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EntityPluginCacheBean {

  public Map<String, EntityPluginCache> getCacheMap() {
    return cacheMap;
  }

  private final Map<String, EntityPluginCache> cacheMap = new ConcurrentHashMap<>(64);

}
