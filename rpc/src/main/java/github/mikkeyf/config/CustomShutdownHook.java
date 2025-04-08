package github.mikkeyf.config;

import github.mikkeyf.registry.zkImpl.util.CuratorUtils;
import github.mikkeyf.remoting.transport.netty.server.NettyRpcServer;
import github.mikkeyf.utils.threadpool.ThreadPoolFactoryUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 niuZi
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-09  12:34
 */
@Slf4j
public class CustomShutdownHook {

    private static final CustomShutdownHook CUSTOM_SHUTDOWN_HOOK = new CustomShutdownHook();

    public static CustomShutdownHook getInstance() {
        return CUSTOM_SHUTDOWN_HOOK;
    }

    public void clearAll() {
        log.info("addShutdownHook for clearAll");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), NettyRpcServer.Port);
                CuratorUtils.clearRegistry(CuratorUtils.getZkClient(), inetSocketAddress);
            } catch (UnknownHostException e) {
                log.error(e.getMessage());
            }
            ThreadPoolFactoryUtil.shutdownAllThreadPools();
        }));
    }
}
