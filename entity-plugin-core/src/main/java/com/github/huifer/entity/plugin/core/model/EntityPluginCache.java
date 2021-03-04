package com.github.huifer.entity.plugin.core.model;

import com.github.huifer.entity.plugin.core.api.EntityConvert;
import org.springframework.data.repository.CrudRepository;

public class EntityPluginCache {

  private String name;
  private Class<? extends EntityConvert> convertClass;
  private CrudRepository crudRepository;
  private Class<?> self;
  private Class<?> idClass;
  private Class<?> insType;
  private Class<?> upType;
  private Class<?> resType;

  public Class<?> getInsType() {
    return insType;
  }

  public void setInsType(Class<?> insType) {
    this.insType = insType;
  }

  public Class<?> getUpType() {
    return upType;
  }

  public void setUpType(Class<?> upType) {
    this.upType = upType;
  }

  public Class<?> getResType() {
    return resType;
  }

  public void setResType(Class<?> resType) {
    this.resType = resType;
  }

  public Class<?> getIdClass() {
    return idClass;
  }

  public void setIdClass(Class<?> idClass) {
    this.idClass = idClass;
  }

  public Class<?> getSelf() {
    return self;
  }

  public void setSelf(Class<?> self) {
    this.self = self;
  }

  public CrudRepository getCrudRepository() {
    return crudRepository;
  }

  public void setCrudRepository(CrudRepository crudRepository) {
    this.crudRepository = crudRepository;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Class<? extends EntityConvert> getConvertClass() {
    return convertClass;
  }

  public void setConvertClass(
      Class<? extends EntityConvert> convertClass) {
    this.convertClass = convertClass;
  }
}
