package github.mikkeyf.remoting.transport;

import github.mikkeyf.extension.SPI;
import github.mikkeyf.remoting.dto.RpcRequest;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-06  21:23
 */
@SPI
public interface RpcRequestTransport {

    /**
     * send rpc request to server and get result
     * @param rpcRequest
     * @return
     */
    Object sendRpcRequestAndGetResult(RpcRequest rpcRequest);
}
