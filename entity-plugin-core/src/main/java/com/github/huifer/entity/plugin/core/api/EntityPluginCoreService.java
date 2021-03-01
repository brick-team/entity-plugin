package com.github.huifer.entity.plugin.core.api;

import java.io.Serializable;

public interface EntityPluginCoreService {

  Object findById(String entityPluginName, String id);

  Object save(String entityPluginName, Object insertParam);

  Object update(String entityPluginName, Object updateParam);

  Boolean deleteById(String entityPluginName, String id);

}
