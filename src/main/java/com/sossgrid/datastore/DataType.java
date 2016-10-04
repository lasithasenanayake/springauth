package com.sossgrid.datastore;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public @interface DataType {
	public String FieldName()  default "";
	public int MinLen() default -1;
	public int MaxLen() default -1;
	public boolean IsPrimary() default false;
}
