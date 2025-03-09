package github.mikkeyf.remoting.transport.netty.server;

import github.mikkeyf.config.CustomShutdownHook;
import github.mikkeyf.entity.RpcServiceEntity;
import github.mikkeyf.factory.SingletonFactory;
import github.mikkeyf.provider.ServiceProvider;
import github.mikkeyf.provider.zkImpl.ZkServiceProviderImpl;
import github.mikkeyf.utils.RuntimeUtil;
import github.mikkeyf.utils.threadpool.ThreadPoolFactoryUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-09  12:14
 */
@Slf4j
@Component
public class NettyRpcServer {

    public static final int Port = 9998;

    private final ServiceProvider serviceProvider = SingletonFactory.getSingleton(ZkServiceProviderImpl.class);

    public void registerService(RpcServiceEntity rpcServiceEntity) {
        serviceProvider.publishService(rpcServiceEntity);
    }

    @SneakyThrows
    public void start() {
        // service a jvm shutdown hook
        CustomShutdownHook.getInstance().clearAll();
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        DefaultEventExecutorGroup serviceHandlerGroup = new DefaultEventExecutorGroup(
                RuntimeUtil.getCpus() * 2,
                ThreadPoolFactoryUtil.createThreadFactory("service-handler-group", false)
        );
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_BACKLOG, 128)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
                        pipeline.addLast(serviceHandlerGroup, new NettyRpcServerHandler());
                    }
                });

    }
}
