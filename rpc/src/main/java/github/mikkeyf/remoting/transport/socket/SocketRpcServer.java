package github.mikkeyf.remoting.transport.socket;

import github.mikkeyf.entity.RpcServiceEntity;
import github.mikkeyf.factory.SingletonFactory;
import github.mikkeyf.provider.ServiceProvider;
import github.mikkeyf.provider.zkImpl.ZkServiceProviderImpl;
import github.mikkeyf.utils.threadpool.ThreadPoolFactoryUtil;
import lombok.extern.slf4j.Slf4j;


import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-02  15:43
 */
@Slf4j
public class SocketRpcServer {

    public static final int PORT = 9998;
    private final ExecutorService threadPool;
    private final ServiceProvider serviceProvider;

    public SocketRpcServer() {
        threadPool = ThreadPoolFactoryUtil.createThreadPoolIfNotExist("socket-rpc-server-pool");
        serviceProvider = SingletonFactory.getSingleton(ZkServiceProviderImpl.class);
    }

    public void registerService(RpcServiceEntity rpcServiceEntity) {
        serviceProvider.publishService(rpcServiceEntity);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket()) {
            String host = InetAddress.getLocalHost().getHostAddress();
            serverSocket.bind(new InetSocketAddress(host, PORT));
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                log.info("client connected [{}]", socket.getInetAddress());
                // 还需要实现客户端接受
            }
        } catch (IOException e) {
            log.error("occur IOException:", e);
        }
    }
}
