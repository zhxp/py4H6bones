package zx.web.config;

import java.lang.annotation.*;

/**
 * Inject the current login user
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
    /**
     * {@code true} if User is attached to a session. Default is {@code false}
     */
    boolean active() default false;
}
