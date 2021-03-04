package com.github.huifer.entity.plugin.core.model;

import com.github.huifer.entity.plugin.core.api.EntityConvert;
import com.github.huifer.entity.plugin.core.api.ValidateApi;
import org.springframework.data.repository.CrudRepository;

public class EntityPluginCache {

  private String name;
  private Class<? extends EntityConvert> convertClass;
  private Class<? extends ValidateApi> validateApiClass;
  private CrudRepository crudRepository;
  private ConvertTypeParam convertTypeParam;
  private ValidateTypeParam validateTypeParam;
  private Class<?> self;
  private Class<?> idClass;

  public ValidateTypeParam getValidateTypeParam() {
    return validateTypeParam;
  }

  public void setValidateTypeParam(
      ValidateTypeParam validateTypeParam) {
    this.validateTypeParam = validateTypeParam;
  }

  public ConvertTypeParam getConvertTypeParam() {
    return convertTypeParam;
  }

  public void setConvertTypeParam(
      ConvertTypeParam convertTypeParam) {
    this.convertTypeParam = convertTypeParam;
  }

  public Class<? extends ValidateApi> getValidateApiClass() {
    return validateApiClass;
  }

  public void setValidateApiClass(
      Class<? extends ValidateApi> validateApiClass) {
    this.validateApiClass = validateApiClass;
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


  public static class ValidateTypeParam {

    private Class<?> insType;
    private Class<?> upType;

    public ValidateTypeParam(Class<?> insType, Class<?> upType) {
      this.insType = insType;
      this.upType = upType;
    }

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
  }

  public static class ConvertTypeParam {

    private Class<?> insType;
    private Class<?> upType;
    private Class<?> resType;

    public ConvertTypeParam(Class<?> insType, Class<?> upType, Class<?> resType) {
      this.insType = insType;
      this.upType = upType;
      this.resType = resType;
    }

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
  }
}
