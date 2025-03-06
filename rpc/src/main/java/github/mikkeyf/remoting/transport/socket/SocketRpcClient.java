package github.mikkeyf.remoting.transport.socket;

import github.mikkeyf.discovery.ServiceDiscovery;
import github.mikkeyf.enums.ServiceDiscoveryEnum;
import github.mikkeyf.extension.ExtensionLoader;
import github.mikkeyf.provider.ServiceProvider;
import github.mikkeyf.remoting.dto.RpcRequest;
import github.mikkeyf.remoting.transport.RpcRequestTransport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-06  21:37
 */
@AllArgsConstructor
@Slf4j
public class SocketRpcClient implements RpcRequestTransport {
    private final ServiceDiscovery serviceDiscovery;

    public SocketRpcClient() {
        this.serviceDiscovery = ExtensionLoader.getExtensionLoader(ServiceDiscovery.class).getExtension(ServiceDiscoveryEnum.ZK.getValue());
    }

    @Override
    public Object sendRpcRequestAndGetResult(RpcRequest rpcRequest) {
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest);
        try (Socket socket = new Socket()) {
            socket.connect(inetSocketAddress);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(rpcRequest);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            //throw new RpcException("调用服务失败:", e);
        }
    }
}
