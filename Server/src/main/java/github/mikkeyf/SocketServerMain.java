package github.mikkeyf;

import github.mikkeyf.entity.RpcServiceEntity;
import github.mikkeyf.remoting.transport.socket.SocketRpcServer;
import github.mikkeyf.serviceImpl.HelloServiceImpl;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-01  23:04
 */
public class SocketServerMain {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        SocketRpcServer socketRpcServer = new SocketRpcServer();
        RpcServiceEntity rpcServiceEntity = new RpcServiceEntity();
        rpcServiceEntity.setService(helloService);
        socketRpcServer.registerService(rpcServiceEntity);
        socketRpcServer.start();
    }
}
