package github.mikkeyf.remoting.netty.server;

import github.mikkeyf.entity.RpcServiceEntity;
import github.mikkeyf.factory.SingletonFactory;
import github.mikkeyf.provider.ServiceProvider;
import github.mikkeyf.provider.zkImpl.ZkServiceProviderImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-09  12:14
 */
@Slf4j
@Component
public class NettyRpcServer {

    public static final int Port = 9998;

    private final ServiceProvider serviceProvider = SingletonFactory.getSingleton(ZkServiceProviderImpl.class);

    public void registerService(RpcServiceEntity rpcServiceEntity) {
        serviceProvider.publishService(rpcServiceEntity);
    }

    @SneakyThrows
    public void start() {

    }
}
