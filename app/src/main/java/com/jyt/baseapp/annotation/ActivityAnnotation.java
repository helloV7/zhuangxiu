package com.jyt.baseapp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by v7 on 2016/12/23.
 */
@Target(ElementType.TYPE)
public @interface ActivityAnnotation {
    public boolean showBack() default false;
    public String title() default "";
    public boolean showFunction() default false;
    public String functionText() default "";
}
