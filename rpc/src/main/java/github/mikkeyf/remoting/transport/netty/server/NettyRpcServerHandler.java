package github.mikkeyf.remoting.transport.netty.server;

import github.mikkeyf.enums.CompressTypeEnum;
import github.mikkeyf.enums.RpcResponseEnum;
import github.mikkeyf.enums.SerializationTypeEnum;
import github.mikkeyf.factory.SingletonFactory;
import github.mikkeyf.handler.RpcRequestHandler;
import github.mikkeyf.remoting.constants.RpcConstants;
import github.mikkeyf.remoting.dto.RpcMessage;
import github.mikkeyf.remoting.dto.RpcRequest;
import github.mikkeyf.remoting.dto.RpcResponse;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-09  13:35
 */
@Slf4j
public class NettyRpcServerHandler extends ChannelInboundHandlerAdapter {

    private final RpcRequestHandler rpcRequestHandler;

    public NettyRpcServerHandler() {
        this.rpcRequestHandler = SingletonFactory.getSingleton(RpcRequestHandler.class);
    }

    /**
     * when the server get a request, use this method
     * @param ctx
     * @param msg message
     */
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    try {
            if (msg instanceof RpcMessage) {
                log.info("server received rpc message:[{}]", msg);
                byte messageType = ((RpcMessage) msg).getMessageType();
                RpcMessage rpcMessage = new RpcMessage();
                rpcMessage.setCodec(SerializationTypeEnum.HESSIAN.getCode());
                rpcMessage.setMessageType(CompressTypeEnum.GZIP.getCode());
                if (messageType == RpcConstants.REQUEST_TYPE) {
                    rpcMessage.setMessageType(RpcConstants.HEARTBEAT_RESPONSE_TYPE);
                    rpcMessage.setData(RpcConstants.PONG);
                } else {
                    RpcRequest rpcRequest = (RpcRequest) ((RpcMessage) msg).getData();
                    Object result = rpcRequestHandler.handle(rpcRequest);
                    log.info(String.format("server get result: %s", result.toString()));
                    rpcMessage.setMessageType(RpcConstants.RESPONSE_TYPE);
                    if (ctx.channel().isActive() && ctx.channel().isWritable()) {
                        RpcResponse<Object> success = RpcResponse.success(result, rpcRequest.getRequestId());
                        rpcMessage.setData(success);
                    } else {
                        RpcResponse<Object> fail = RpcResponse.fail(RpcResponseEnum.FAIL);
                        rpcMessage.setData(fail);
                        log.error("not writable now, message dropped");
                    }
                }
                ctx.writeAndFlush(rpcMessage).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        } finally{
            ReferenceCountUtil.release(msg);
        }
    }
}
