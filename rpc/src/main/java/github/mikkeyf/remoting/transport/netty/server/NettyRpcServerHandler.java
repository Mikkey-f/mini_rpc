package github.mikkeyf.remoting.transport.netty.server;

import github.mikkeyf.factory.SingletonFactory;
import github.mikkeyf.handler.RpcRequestHandler;
import github.mikkeyf.remoting.dto.RpcMessage;
import github.mikkeyf.remoting.dto.RpcRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
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

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof RpcMessage) {
            log.info("server received rpc message:[{}]", msg);
            byte messageType = ((RpcMessage) msg).getMessageType();
            RpcMessage rpcMessage = new RpcMessage();
            rpcMessage.setCodec(SerializationT);
        }
    }

}
