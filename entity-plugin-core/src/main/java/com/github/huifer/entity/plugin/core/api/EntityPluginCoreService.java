package com.github.huifer.entity.plugin.core.api;

import java.io.Serializable;

public interface EntityPluginCoreService {

  Object findById(String entityPluginName, String id) throws Exception;

  Object save(String entityPluginName, Object insertParam) throws Exception;

  Object update(String entityPluginName, Object updateParam) throws Exception;

  Boolean deleteById(String entityPluginName, String id);

}
