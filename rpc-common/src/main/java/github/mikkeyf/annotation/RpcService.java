package github.mikkeyf.annotation;

import java.lang.annotation.*;

/**
 * RPC service annotation, marked on the service implementation class
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface RpcService {
    String version() default "";
    String group() default "";
}
