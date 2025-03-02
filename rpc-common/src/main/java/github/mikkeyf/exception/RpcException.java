package github.mikkeyf.exception;

import github.mikkeyf.enums.RpcErrorMessageEnum;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-02  18:53
 */
public class RpcException extends RuntimeException {
    public RpcException(RpcErrorMessageEnum rpcErrorMessageEnum, String detail) {
        super(rpcErrorMessageEnum.getMessage() + ":" + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcErrorMessageEnum rpcErrorMessageEnum) {
        super(rpcErrorMessageEnum.getMessage());
    }
}
