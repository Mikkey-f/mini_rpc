package github.mikkeyf.register.zkImpl;

import github.mikkeyf.register.ServiceRegistry;
import lombok.extern.slf4j.Slf4j;
import github.mikkeyf.register.zkImpl.util.CuratorUtils;

import java.net.InetSocketAddress;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-02  19:32
 */
@Slf4j
public class ZkServiceRegistryImpl implements ServiceRegistry {
    @Override
    public void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress) {
        String servicePath = CuratorUtils.ZK_REGISTER_ROOT_PATH + "/" + rpcServiceName + inetSocketAddress.toString();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        CuratorUtils.createPersistentNode(zkClient, servicePath);
    }
}
