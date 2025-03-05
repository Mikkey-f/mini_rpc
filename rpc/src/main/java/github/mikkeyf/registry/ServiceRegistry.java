package github.mikkeyf.registry;

import github.mikkeyf.extension.SPI;

import java.net.InetSocketAddress;

/**
 * service registration
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-02  18:41
 */
@SPI
public interface ServiceRegistry {

    /**
     * registry service
     * @param rpcServiceName rpc service name
     * @param inetSocketAddress service address
     */
    void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress);
}
