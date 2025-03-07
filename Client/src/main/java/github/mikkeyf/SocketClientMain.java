package github.mikkeyf;

import github.mikkeyf.entity.RpcServiceEntity;
import github.mikkeyf.proxy.RpcClientProxy;
import github.mikkeyf.remoting.transport.RpcRequestTransport;
import github.mikkeyf.remoting.transport.socket.SocketRpcClient;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-06  21:16
 */
public class SocketClientMain {
    public static void main(String[] args) {
        RpcRequestTransport socketRpcClient = new SocketRpcClient();
        RpcServiceEntity rpcServiceEntity = new RpcServiceEntity();
        RpcClientProxy rpcClientProxy = new RpcClientProxy(socketRpcClient, rpcServiceEntity);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        String hello = helloService.hello(new Hello("111", "222"));
        System.out.println(hello);
    }
}
