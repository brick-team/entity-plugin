package com.github.huifer.entity.plugin.core.api.impl;

import com.github.huifer.entity.plugin.core.api.BeanFindService;
import com.github.huifer.entity.plugin.core.api.EntityConvert;
import com.github.huifer.entity.plugin.core.api.ValidateApi;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class BeanFindServiceImpl implements BeanFindService {

  private static final Logger log = LoggerFactory.getLogger(BeanFindServiceImpl.class);
  @Autowired
  private ApplicationContext context;

  @Override
  public ValidateApi findValidateApi(Class<? extends ValidateApi> clazz) {
    List<String> beanNamesByType = this.findBeanNamesByType(clazz);
    if (CollectionUtils.isEmpty(beanNamesByType)) {
      return null;
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
    // todo: 2021/3/1 优先级处理
    return beans;
  }

  @Override
  public EntityConvert findEntityConvert(Class<? extends EntityConvert> clazz) {
    return null;
  }

  private List<String> findBeanNamesByType(Class<?> clazz) {
    if (clazz != null) {
      String[] beanNamesForType = context.getBeanNamesForType(clazz);
      return Arrays.stream(beanNamesForType).collect(Collectors.toList());
    }
    return Collections.emptyList();
  }

  private boolean hasBeanName(Class<?> clazz) {
    return findBeanNamesByType(clazz).isEmpty();
  }
}
