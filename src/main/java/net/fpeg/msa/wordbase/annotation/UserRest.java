package net.fpeg.msa.wordbase.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface UserRest {
    Class<?>[] checkFields() ;
    boolean ignoreEdit() default false;
}
