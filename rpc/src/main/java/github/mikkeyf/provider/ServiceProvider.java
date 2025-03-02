package github.mikkeyf.provider;

import github.mikkeyf.entity.RpcServiceEntity;

/**
 * store and provide service object.
 *
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-02  15:44
 */
public interface ServiceProvider {

    /**
     * @param rpcServiceEntity rpc service related attributes
     */
    void addService(RpcServiceEntity rpcServiceEntity);

    /**
     * to select the service object with service name
     * @param rpcServiceName rpc service name
     * @return service object
     */
    Object getService(String rpcServiceName);

    /**
     * @param rpcServiceEntity rpc service related attributes
     */
    void publishService(RpcServiceEntity rpcServiceEntity);
}
