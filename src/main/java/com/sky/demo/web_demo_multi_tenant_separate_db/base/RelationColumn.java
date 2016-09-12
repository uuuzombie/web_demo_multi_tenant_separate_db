package com.sky.demo.web_demo_multi_tenant_separate_db.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by rg on 2015/7/16.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RelationColumn {

    String value();
    boolean isShield() default false;
}
