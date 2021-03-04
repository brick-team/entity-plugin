package com.github.huifer.entity.plugin.core.api;

public interface BeanFindService {

  ValidateApi findValidateApi(Class<? extends  ValidateApi> clazz) throws Exception;

  EntityConvert findEntityConvert(Class<? extends  EntityConvert> clazz) throws Exception;
}
