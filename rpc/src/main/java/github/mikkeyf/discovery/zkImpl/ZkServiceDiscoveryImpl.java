package github.mikkeyf.discovery.zkImpl;

import github.mikkeyf.discovery.ServiceDiscovery;
import github.mikkeyf.registry.zkImpl.util.CuratorUtils;
import github.mikkeyf.remoting.dto.RpcRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-06  21:49
 */
@Slf4j
public class ZkServiceDiscoveryImpl implements ServiceDiscovery {


    @Override
    public InetSocketAddress lookupService(RpcRequest rpcRequest) {
        String rpcServiceName = rpcRequest.getRpcServiceName();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        List<String> serviceUrlList = CuratorUtils.getChildrenNodes(zkClient, rpcServiceName);

    }
}
