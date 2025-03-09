package github.mikkeyf;

import github.mikkeyf.annotation.RpcScan;
import github.mikkeyf.proxy.RpcClientProxy;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-09  12:06
 */
@RpcScan(basePackages = {"github.mikkeyf"})
public class NettyServerMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyServerMain.class);
         nettyRpcServer = applicationContext.getBean("nettyRpcServer");
    }
}
