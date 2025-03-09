package github.mikkeyf;

import github.mikkeyf.annotation.RpcScan;
import github.mikkeyf.entity.RpcServiceEntity;
import github.mikkeyf.remoting.transport.netty.server.NettyRpcServer;
import github.mikkeyf.serviceImpl.HelloServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-09  12:06
 */
@RpcScan(basePackages = {"github.mikkeyf"})
public class NettyServerMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyServerMain.class);
        NettyRpcServer nettyRpcServer = (NettyRpcServer) applicationContext.getBean("nettyRpcServer");

        HelloServiceImpl helloService = new HelloServiceImpl();
        RpcServiceEntity rpcServiceEntity = RpcServiceEntity.builder()
                .group("test2").version("2.0").service(helloService).build();
        nettyRpcServer.registerService(rpcServiceEntity);
        nettyRpcServer.start();
    }
}
