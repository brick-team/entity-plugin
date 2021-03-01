package com.github.huifer.entity.plugin.core.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 接口反射工具
 *
 * @author huifer
 */
public class InterfaceReflectUtils {

  private InterfaceReflectUtils() {

  }

  public static List<Class<?>> getInterfaceGenericLasses(Class<?> check, Class<?> targetClass) {

    if (check == null || targetClass == null) {
      return Collections.emptyList();
    }
    List<Class<?>> res = new ArrayList<>();

    Class<?> cur = check;

    while (cur != null && cur != Object.class) {
      Type[] types = cur.getGenericInterfaces();
      for (Type type : types) {

        // todo: 修改为可以根据类型进行推论
        if (type.getTypeName().contains(targetClass.getName())) {
          Type[] typeArguments = ((ParameterizedType) type).getActualTypeArguments();
          for (Type typeArgument : typeArguments) {
            if (typeArgument instanceof Class) {
              res.add((Class<?>) typeArgument);
            }
          }
          break;

        }
      }
      Class<?>[] interfaces = cur.getInterfaces();
      if (interfaces != null) {
        for (Class<?> inter : interfaces) {
          List<Class<?>> result = getInterfaceGenericLasses(inter, targetClass);
          if (result != null) {
            res.addAll(result);
          }
        }
      }
      cur = cur.getSuperclass();
    }

    return res;
  }


}
