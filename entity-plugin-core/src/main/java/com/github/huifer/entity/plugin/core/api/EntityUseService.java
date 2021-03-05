package com.github.huifer.entity.plugin.core.api;

public interface EntityUseService {

  <T> T findById(String id, Class<T> clazz);

  <T> T save(Object insertParam, Class<T> clazz);

  <T> T update(Object updateParam, Class<T> clazz);

  Boolean deleteById(String id, Class<?> clazz);


}
