package github.mikkeyf.utils.threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-02  16:07
 */
@Slf4j
public class ThreadPoolFactoryUtil {

    /**
     * Note that ConcurrentHashMap is thread-safe,
     * and we define threadNamePrefix to distinguish different thread pools,
     * with one thread pool corresponding to one service.
     * key: threadNamePrefix
     * value: threadPool -> An implementation of the thread pool instance that implements the ExecutorService interface
     */
    private static final Map<String, ExecutorService> THREAD_POOLS = new ConcurrentHashMap<>();

    public ThreadPoolFactoryUtil() {
    }

    public static ExecutorService createThreadPoolIfNotExist(String threadNamePrefix) {
        CustomThreadPoolConfig customThreadPoolConfig = new CustomThreadPoolConfig();
        return createThreadPoolIfNotExist(threadNamePrefix, customThreadPoolConfig, false);
    }

    public static ExecutorService createThreadPoolIfNotExist(String threadNamePrefixk, CustomThreadPoolConfig customThreadPoolConfig, Boolean daemon) {
        ExecutorService threadPool = THREAD_POOLS.computeIfAbsent(threadNamePrefixk,
                k -> createThreadPool(customThreadPoolConfig, threadNamePrefixk, daemon));
        if (threadPool.isShutdown() || threadPool.isTerminated()) {
            THREAD_POOLS.remove(threadNamePrefixk);
            threadPool = createThreadPoolIfNotExist(threadNamePrefixk, customThreadPoolConfig, daemon);
            THREAD_POOLS.put(threadNamePrefixk, threadPool);
        }
        return threadPool;
    }

    private static ExecutorService createThreadPool(CustomThreadPoolConfig customThreadPoolConfig, String threadNamePrefix, Boolean daemon) {
        ThreadFactory threadFactory = createThreadFactory(threadNamePrefix, daemon);
        return new ThreadPoolExecutor(customThreadPoolConfig.getCorePoolSize(), customThreadPoolConfig.getMaximumPoolSize(),
                customThreadPoolConfig.getKeepAliveTime(), customThreadPoolConfig.getTimeUnit(), customThreadPoolConfig.getWorkQueue(),
                threadFactory);
    }

    public static ThreadFactory createThreadFactory(String threadNamePrefix, Boolean daemon) {
        if (threadNamePrefix != null) {
            if (daemon != null) {
                return new ThreadFactoryBuilder()
                        .setNameFormat(threadNamePrefix + "-%d")
                        .setDaemon(daemon).build();
            } else {
                return new ThreadFactoryBuilder().setNameFormat(threadNamePrefix + "-%d").build();
            }
        }
        return Executors.defaultThreadFactory();
    }

    /**
     * shutdown all threadPoll
     */
    public static void shutdownAllThreadPools() {
        log.info("shutdown all thread pools");
        THREAD_POOLS.entrySet().parallelStream().forEach(entry -> {
            ExecutorService executorService = entry.getValue();
            executorService.shutdown();
            log.info("shutdown thread pool [{}],[{}]", entry.getKey(), executorService.isShutdown());
            try {
                executorService.awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                log.error("Thread pool awaitTermination interrupted", e);
                executorService.shutdownNow();
            }
        });
    }

}
