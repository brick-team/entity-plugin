package com.github.huifer.entity.plugin.core.api;

public interface BeanFindService {

  ValidateApi findValidateApi(Class<? extends  ValidateApi> clazz);

  EntityConvert findEntityConvert(Class<? extends  EntityConvert> clazz);
}
