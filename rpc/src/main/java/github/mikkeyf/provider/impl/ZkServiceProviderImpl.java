package github.mikkeyf.provider.impl;

import github.mikkeyf.entity.RpcServiceEntity;
import github.mikkeyf.enums.RpcErrorMessageEnum;
import github.mikkeyf.enums.ServiceRegistryEnum;
import github.mikkeyf.exception.RpcException;
import github.mikkeyf.extension.ExtensionLoader;
import github.mikkeyf.provider.ServiceProvider;
import github.mikkeyf.registry.ServiceRegistry;
import github.mikkeyf.remoting.socket.SocketRpcServer;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-02  18:14
 */
@Slf4j
public class ZkServiceProviderImpl implements ServiceProvider {

    /**
     * key: rpc service name(interface name + version + group)
     * value: service object
     */
    private final Map<String, Object> serviceMap;
    private final Set<String> registeredServices;
    private final ServiceRegistry serviceRegistry;

    public ZkServiceProviderImpl() {
        serviceMap = new ConcurrentHashMap<>();
        registeredServices = ConcurrentHashMap.newKeySet();
        serviceRegistry = ExtensionLoader.getExtensionLoader(ServiceRegistry.class).getExtension(ServiceRegistryEnum.ZK.getName());
    }

    @Override
    public void addService(RpcServiceEntity rpcServiceEntity) {
        String rpcServiceName = rpcServiceEntity.getRpcServiceName();
        Object service = serviceMap.get(rpcServiceName);
        if (service != null) {
            return;
        }
        registeredServices.add(rpcServiceName);
        serviceMap.put(rpcServiceName, rpcServiceEntity.getService());
        log.info("Add service: {} and interfaces:{}", rpcServiceName, rpcServiceEntity.getService().getClass().getInterfaces());
    }

    @Override
    public Object getService(String rpcServiceName) {
        Object service = serviceMap.get(rpcServiceName);
        if (service == null) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND);
        }
        return service;
    }

    @Override
    public void publishService(RpcServiceEntity rpcServiceEntity) {
        try {
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            this.addService(rpcServiceEntity);
            serviceRegistry.registerService(rpcServiceEntity.getRpcServiceName(), new InetSocketAddress(hostAddress, SocketRpcServer.PORT));
        } catch (UnknownHostException e) {
            log.error("occur exception when getHostAddress", e);
        }
    }
}
