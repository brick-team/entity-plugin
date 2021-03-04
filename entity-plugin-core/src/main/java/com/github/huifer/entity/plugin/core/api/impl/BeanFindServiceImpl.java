package com.github.huifer.entity.plugin.core.api.impl;

import com.github.huifer.entity.plugin.core.api.BeanFindService;
import com.github.huifer.entity.plugin.core.api.EntityConvert;
import com.github.huifer.entity.plugin.core.api.ValidateApi;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class BeanFindServiceImpl implements BeanFindService {

  private static final Logger log = LoggerFactory.getLogger(BeanFindServiceImpl.class);
  @Autowired
  private ApplicationContext context;

  @Override
  public ValidateApi findValidateApi(Class<? extends ValidateApi> clazz) throws Exception {
    if (log.isInfoEnabled()) {
      log.info("findValidateApi,clazz = {}", clazz);
    }
    if (clazz == null) {
      throw new RuntimeException("ValidateApi class is null");
    }
    List<String> beanNamesByType = this.findBeanNamesByType(clazz);
    if (CollectionUtils.isEmpty(beanNamesByType)) {
      return classGenValidateApi(clazz);
    }
    ValidateApi validateApi = determineValidateApi(validateApis(beanNamesByType, clazz));
    if (validateApi == null) {
      return classGenValidateApi(clazz);
    }
    return validateApi;
  }

  private ValidateApi classGenValidateApi(Class<? extends ValidateApi> clazz)
      throws NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
    Constructor<? extends ValidateApi> declaredConstructor = clazz.getDeclaredConstructor();
    return declaredConstructor.newInstance();
  }

  @Override
  public EntityConvert findEntityConvert(Class<? extends EntityConvert> clazz) throws Exception {
    if (log.isInfoEnabled()) {
      log.info("findEntityConvert,clazz = {}", clazz);
    }
    if (clazz == null) {
      throw new RuntimeException("EntityConvert class is null");
    }
    List<String> beanNamesByType = this.findBeanNamesByType(clazz);
    if (CollectionUtils.isEmpty(beanNamesByType)) {
      return classGenEntityConvert(clazz);
    }
    EntityConvert entityConvert = determineEntityConvertApi(
        entityConvertApis(beanNamesByType, clazz));
    if (entityConvert == null) {
      return classGenEntityConvert(clazz);
    }
    return entityConvert;

  }

  private EntityConvert classGenEntityConvert(Class<? extends EntityConvert> clazz)
      throws NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
    Constructor<? extends EntityConvert> declaredConstructor = clazz.getDeclaredConstructor();
    return declaredConstructor.newInstance();
  }

  private EntityConvert determineEntityConvertApi(List<EntityConvert> entityConverts) {
    if (CollectionUtils.isEmpty(entityConverts)) {
      return null;
    }
    EntityConvert entityConvert = null;
    Map<Integer, EntityConvert> entityConvertHashMap = new HashMap<>();

    for (EntityConvert bean : entityConverts) {
      // 优先级最高
      if (bean instanceof PriorityOrdered) {
        entityConvert = bean;
        break;
      }
      if (bean instanceof Ordered) {
        int order = ((Ordered) bean).getOrder();
        entityConvertHashMap.put(order, bean);
      }
    }

    // 如果在第一层循环时得到了直接返回
    if (entityConvert != null) {
      return entityConvert;
    }

    // 处理Ordered情况
    if (CollectionUtils.isEmpty(entityConvertHashMap)) {
      return entityConverts.get(0);
    } else {
      Set<Integer> key = entityConvertHashMap.keySet();
      // 取优先级最高的
      Optional<Integer> max = key.stream().max(Integer::compareTo);
      if (max.isPresent()) {
        return entityConvertHashMap.get(max.get());
      }
    }

    return null;
  }

  private List<EntityConvert> entityConvertApis(List<String> beanNames,
      Class<? extends EntityConvert> clazz) {
    List<EntityConvert> beans = new ArrayList<>();

    for (String beanName : beanNames) {
      EntityConvert bean = context.getBean(beanName, clazz);
      beans.add(bean);
    }

    return beans;
  }

  private ValidateApi determineValidateApi(List<ValidateApi> validateApis) {
    if (CollectionUtils.isEmpty(validateApis)) {
      return null;
    }
    ValidateApi validateApi = null;
    Map<Integer, ValidateApi> validateApiMap = new HashMap<>();

    for (ValidateApi bean : validateApis) {
      // 优先级最高
      if (bean instanceof PriorityOrdered) {
        validateApi = bean;
        break;
      }
      if (bean instanceof Ordered) {
        int order = ((Ordered) bean).getOrder();
        validateApiMap.put(order, bean);
      }
    }

    // 如果在第一层循环时得到了直接返回
    if (validateApi != null) {
      return validateApi;
    }

    // 处理Ordered情况
    if (CollectionUtils.isEmpty(validateApiMap)) {
      return validateApis.get(0);
    } else {
      Set<Integer> key = validateApiMap.keySet();
      // 取优先级最高的
      Optional<Integer> max = key.stream().max(Integer::compareTo);
      if (max.isPresent()) {
        return validateApiMap.get(max.get());
      }
    }

    return null;
  }

  private List<ValidateApi> validateApis(List<String> beanNames,
      Class<? extends ValidateApi> clazz) {
    List<ValidateApi> beans = new ArrayList<>();

    for (String beanName : beanNames) {
      ValidateApi validateApi = context.getBean(beanName, clazz);
      beans.add(validateApi);
    }

    return beans;
  }


  private List<String> findBeanNamesByType(Class<?> clazz) {
    if (clazz != null) {
      String[] beanNamesForType = context.getBeanNamesForType(clazz);
      return Arrays.stream(beanNamesForType).collect(Collectors.toList());
    }
    return Collections.emptyList();
  }


}
