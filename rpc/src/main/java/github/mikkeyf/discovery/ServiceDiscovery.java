package github.mikkeyf.discovery;

import github.mikkeyf.extension.SPI;
import github.mikkeyf.remoting.dto.RpcRequest;

import java.net.InetSocketAddress;

/**
 * service discovery
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-06  21:47
 */
@SPI
public interface ServiceDiscovery {
    /**
     * Dynamically query the host address of the service,
     * Because we cannot guarantee the stability of the service's address
     *
     * @param rpcRequest rpc service pojo
     * @return service address
     */
    InetSocketAddress lookupService(RpcRequest rpcRequest);
}
