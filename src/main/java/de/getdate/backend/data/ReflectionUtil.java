package de.getdate.backend.data;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.apache.commons.lang3.Validate;

public class ReflectionUtil {
  private ReflectionUtil() {
  }

  public static <T> Class<T> getGenericType(Class<?> clazz, int typeIndex) {
    Validate.notNull(clazz, "The clazz must not be null.");

    Type[] types = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments();

    Validate.isTrue(typeIndex >= 0 && typeIndex < types.length,
        "The typeIndex must be between 0 and the length of types");

    return (Class<T>) types[typeIndex];
  }

}
