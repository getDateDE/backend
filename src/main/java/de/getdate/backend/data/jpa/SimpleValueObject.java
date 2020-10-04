package de.getdate.backend.data.jpa;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SimpleValueObject {

  /**
   * @return the field name of the simple value object
   */
  String value() default "value";

}
