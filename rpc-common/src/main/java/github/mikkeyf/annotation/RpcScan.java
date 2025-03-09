package github.mikkeyf.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import()
@Documented
public @interface RpcScan {

    String[] basePackages();
}
