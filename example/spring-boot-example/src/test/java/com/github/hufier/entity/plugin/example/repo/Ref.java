package com.github.hufier.entity.plugin.example.repo;

import com.github.huifer.entity.plugin.core.utils.InterfaceReflectUtils;
import com.github.huifer.entity.plugin.example.repo.OauthClientRepo;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

public class Ref {

  /**
   * 获取所有接口
   */
  public static Set<Class<?>> getG(Class<?> clazz, Set<Class<?>> data) {
    Set<Class<?>> res = new HashSet<>(data);
    Class<?>[] interfaces = clazz.getInterfaces();
    res.addAll(Arrays.asList(interfaces));
    for (Class<?> anInterface : interfaces) {
      Class<?>[] interfaces1 = anInterface.getInterfaces();
      res.addAll(Arrays.asList(interfaces1));
      for (Class<?> aClass : interfaces1) {
        Set<Class<?>> g = getG(aClass, res);
        res.addAll(g);
      }
    }
    return res;
  }

  public static Set<Class<?>> getG(Class<?> clazz) {
    Set<Class<?>> classes = new HashSet<>(10);
    return getG(clazz, classes);
  }


  public static void main(String[] args) {

    Class<OauthClientRepo> clazz = OauthClientRepo.class;

    Type[] genericInterfaces = clazz.getGenericInterfaces();

    for (Type genericInterface : genericInterfaces) {
      // 处理当前层级的单个泛型
      if (genericInterface instanceof ParameterizedTypeImpl) {
        // 接口类型
        Class<?> rawType = ((ParameterizedTypeImpl) genericInterface).getRawType();
        // 泛型数据
        Type[] actualTypeArguments = ((ParameterizedTypeImpl) genericInterface)
            .getActualTypeArguments();

        Class<?>[] interfaces = rawType.getInterfaces();
        for (Class<?> anInterface : interfaces) {
          Type[] abc = anInterface.getGenericInterfaces();
          System.out.println();
        }
        boolean from = InterfaceReflectUtils.isFrom(Repository.class, rawType);
        System.out.println();
      }

    }

    System.out.println(InterfaceReflectUtils.isFrom(CrudRepository.class, JpaRepository.class));
    System.out.println();
  }

}
