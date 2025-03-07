package github.mikkeyf.proxy;

import github.mikkeyf.entity.RpcServiceEntity;
import github.mikkeyf.enums.RpcErrorMessageEnum;
import github.mikkeyf.enums.RpcResponseEnum;
import github.mikkeyf.exception.RpcException;
import github.mikkeyf.remoting.dto.RpcRequest;
import github.mikkeyf.remoting.dto.RpcResponse;
import github.mikkeyf.remoting.transport.RpcRequestTransport;
import github.mikkeyf.remoting.transport.socket.SocketRpcClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 *  It is precisely because of the dynamic proxy that
 *  the remote method called by the client is like calling the local method (the intermediate process is shielded)
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-07  11:21
 */
@Slf4j
public class RpcClientProxy implements InvocationHandler {

    private static final String INTERFACE_NAME = "";

    /**
     * Used to send requests to the server
     */
    private final RpcRequestTransport rpcRequestTransport;
    private final RpcServiceEntity rpcServiceEntity;

    public RpcClientProxy(RpcRequestTransport rpcRequestTransport, RpcServiceEntity rpcServiceEntity) {
        this.rpcRequestTransport = rpcRequestTransport;
        this.rpcServiceEntity = rpcServiceEntity;
    }


    public RpcClientProxy(RpcRequestTransport rpcRequestTransport) {
        this.rpcRequestTransport = rpcRequestTransport;
        rpcServiceEntity = new RpcServiceEntity();
    }

    /**
     * get the proxy Object
     * @return
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T)Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
    }

    /**
     * This method is actually called when you use a proxy object to call a method.
     * The proxy object is the object you get through the getProxy method.
     */
    @SneakyThrows
    @SuppressWarnings("unchecked")
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("invoke method: [{}]", method.getName());
        RpcRequest rpcRequest = RpcRequest.builder().methodName(method.getName())
                .parameters(args)
                .group(rpcServiceEntity.getGroup())
                .version(rpcServiceEntity.getVersion())
                .interfaceName(method.getDeclaringClass().getName())
                .parameterTypes(method.getParameterTypes())
                .requestId(UUID.randomUUID().toString())
                .build();
        RpcResponse<Object> rpcResponse = null;
        if (rpcRequestTransport instanceof SocketRpcClient) {
            rpcResponse = (RpcResponse<Object>) rpcRequestTransport.sendRpcRequestAndGetResult(rpcRequest);
        }
        this.check(rpcResponse, rpcRequest);
        return rpcResponse.getData();
    }

    private void check(RpcResponse rpcResponse, RpcRequest rpcRequest) {
        if (rpcResponse == null) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }

        /**
         * this response is not this request. Not match
         */
        if (!rpcRequest.getRequestId().equals(rpcResponse.getRequestId())) {
            throw new RpcException(RpcErrorMessageEnum.REQUEST_NOT_MATCH_RESPONSE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }

        if (rpcResponse.getCode() == null || !rpcResponse.getCode().equals(RpcResponseEnum.SUCCESS.getCode())) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }

    }
}
