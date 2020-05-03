package com.own.midterm.util.BusUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventUtil {
    ThreadModel threadModel() default ThreadModel.DEFAULT;
}
