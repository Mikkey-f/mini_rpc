package github.mikkeyf.discovery.zkImpl;

import github.mikkeyf.discovery.ServiceDiscovery;
import github.mikkeyf.enums.RpcErrorMessageEnum;
import github.mikkeyf.exception.RpcException;
import github.mikkeyf.registry.zkImpl.util.CuratorUtils;
import github.mikkeyf.remoting.dto.RpcRequest;
import github.mikkeyf.utils.CollectionUtil;
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
        if (CollectionUtil.isEmpty(serviceUrlList)) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND, rpcServiceName);
        }
        String[] socketAddressArray = serviceUrlList.get(0).split(":");
        String host = socketAddressArray[0];
        int port = Integer.parseInt(socketAddressArray[1]);
        return new InetSocketAddress(host, port);
    }
}
