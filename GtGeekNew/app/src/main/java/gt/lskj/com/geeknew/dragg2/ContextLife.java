package gt.lskj.com.geeknew.dragg2;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Home on 16/9/21.
 */

@Qualifier
@Documented
@Retention(RUNTIME)
public @interface ContextLife {
    String value() default "Application";
}
