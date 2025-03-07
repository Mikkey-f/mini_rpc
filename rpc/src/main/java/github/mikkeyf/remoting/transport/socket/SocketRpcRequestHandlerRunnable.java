package github.mikkeyf.remoting.transport.socket;

import github.mikkeyf.handler.RpcRequestHandler;
import github.mikkeyf.proxy.RpcClientProxy;
import lombok.extern.slf4j.Slf4j;

import java.net.Socket;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-07  17:15
 */
@Slf4j
public class SocketRpcRequestHandlerRunnable {
    private final Socket socket;
    private final RpcRequestHandler rpcRequestHandler;


}
