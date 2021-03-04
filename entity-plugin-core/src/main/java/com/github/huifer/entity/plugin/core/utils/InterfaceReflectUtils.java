package com.github.huifer.entity.plugin.core.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

/**
 * 接口反射工具
 *
 * @author huifer
 */
public class InterfaceReflectUtils {

  private InterfaceReflectUtils() {

  }

  /**
   * source is from target?
   *
   * @param source
   * @param target
   * @return
   */
  public static boolean isFrom(Class<?> source, Class<?> target) {
    return source.isAssignableFrom(target);
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

        if (type instanceof ParameterizedTypeImpl) {

          if (targetClass.isAssignableFrom(((ParameterizedTypeImpl) type).getRawType())) {
            Type[] typeArguments = ((ParameterizedType) type).getActualTypeArguments();
            for (Type typeArgument : typeArguments) {
              if (typeArgument instanceof Class) {
                res.add((Class<?>) typeArgument);
              }
            }
            break;

          }
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
