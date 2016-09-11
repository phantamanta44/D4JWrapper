package io.github.phantamanta44.discord4j.core.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Module {

    String id();

    String name() default "Unnamed";

    String desc() default "No description provided.";

    String author() default "Unknown";

    String[] deps() default {};

    boolean enabledDefault() default true;

}
