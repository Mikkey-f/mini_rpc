package github.mikkeyf.remoting.transport.socket;

import github.mikkeyf.factory.SingletonFactory;
import github.mikkeyf.handler.RpcRequestHandler;
import github.mikkeyf.proxy.RpcClientProxy;
import github.mikkeyf.remoting.dto.RpcRequest;
import github.mikkeyf.remoting.dto.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-07  17:15
 */
@Slf4j
public class SocketRpcRequestHandlerRunnable implements Runnable {
    private final Socket socket;
    private final RpcRequestHandler rpcRequestHandler;

    public SocketRpcRequestHandlerRunnable(Socket socket) {
        this.socket = socket;
        this.rpcRequestHandler = SingletonFactory.getSingleton(RpcRequestHandler.class);
    }


    @Override
    public void run() {
        log.info("server handle message from client by thread: [{}]", Thread.currentThread().getName());
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            Object result = rpcRequestHandler.handle(rpcRequest);
            objectOutputStream.writeObject(RpcResponse.success(result, rpcRequest.getRequestId()));
            objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException e ) {
            throw new RuntimeException(e);
        }
    }
}
