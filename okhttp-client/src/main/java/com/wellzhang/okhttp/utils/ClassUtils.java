package com.wellzhang.okhttp.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhangxiang
 * @Description: 判断师傅为基础类型
 * @version 1.0
 * @date 2020/6/21 20:47
 */
public class ClassUtils {

  private static final Set<Class<?>> primitiveWrapperSet = new HashSet<>();

  static {
    primitiveWrapperSet.add(Boolean.class);
    primitiveWrapperSet.add(Byte.class);
    primitiveWrapperSet.add(Character.class);
    primitiveWrapperSet.add(Short.class);
    primitiveWrapperSet.add(Integer.class);
    primitiveWrapperSet.add(Long.class);
    primitiveWrapperSet.add(Double.class);
    primitiveWrapperSet.add(Float.class);
    primitiveWrapperSet.add(BigDecimal.class);
    primitiveWrapperSet.add(BigInteger.class);
    primitiveWrapperSet.add(String.class);
    primitiveWrapperSet.add(java.util.Date.class);
    primitiveWrapperSet.add(java.sql.Date.class);
    primitiveWrapperSet.add(java.sql.Time.class);
    primitiveWrapperSet.add(java.sql.Timestamp.class);

  }

  public static boolean isPrimitive(Class<?> clazz){
    return clazz.isPrimitive() //
        || primitiveWrapperSet.contains(clazz)
        || clazz.isEnum() //
        ;
  }
}
