package github.mikkeyf.loadbalance.loadbalancer;

import github.mikkeyf.loadbalance.AbstractLoadBalance;
import github.mikkeyf.remoting.dto.RpcRequest;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-08  16:00
 */
@Slf4j
public class ConsistentHashLoadBalance extends AbstractLoadBalance {
    //k - serviceName v - to get virtualInvokers
    private final ConcurrentHashMap<String, ConsistentHashSelector> selectors = new ConcurrentHashMap<>();

    @Override
    protected String doSelect(List<String> serviceUrlList, RpcRequest rpcRequest) {
        int urlListHashCode = System.identityHashCode(serviceUrlList);
        String rpcServiceName = rpcRequest.getRpcServiceName();
        ConsistentHashSelector selector = selectors.get(rpcServiceName);
        // if serviceName is null or is updated, we update selector
        if (selector == null || selector.identityHashCode != urlListHashCode) {
            selectors.put(rpcServiceName, new ConsistentHashSelector(serviceUrlList, 160, urlListHashCode));
            selector = selectors.get(rpcServiceName);
        }
        return selector.select(rpcServiceName + Arrays.stream(rpcRequest.getParameters()));
    }

    static class ConsistentHashSelector {
        // k - virtual hash value v - inetAddress
        private final TreeMap<Long, String> virtualInvokers;

        private final int identityHashCode;

        public ConsistentHashSelector(List<String> invokers, int replicaNumber, int identityHashCode) {
            this.virtualInvokers = new TreeMap<>();
            this.identityHashCode = identityHashCode;

            for (String invoker : invokers) {
                for (int i = 0; i < replicaNumber / 4; i++) {
                    //128 byte hash key value
                    byte[] bytes = md5(invoker + i);
                    for (int h = 0; h < 4; h++) {
                        // bytes splits for 4 section
                        long m = hash(bytes, h);
                        virtualInvokers.put(m, invoker);
                    }
                }
            }
        }

        static byte[] md5(String key) {
            MessageDigest digest;
            try {
                digest = MessageDigest.getInstance("MD5");
                byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
                digest.update(bytes, 0, bytes.length);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            return digest.digest();
        }

        static long hash(byte[] digest, int idx) {
            return ((long) (digest[3 + idx * 4] & 255) << 24 | (long) (digest[2 + idx * 4] & 255) << 16 | (long) (digest[1 + idx * 4] & 255) << 8 | (long) (digest[idx * 4] & 255)) & 4294967295L;
        }

        public String select(String rpcServiceKey) {
            byte[] digest = md5(rpcServiceKey);
            return selectForKey(hash(digest, new Random().nextInt(4)));
        }

        public String selectForKey(long hashCode) {
            Map.Entry<Long, String> entry = virtualInvokers.tailMap(hashCode, true).firstEntry();
            if (entry == null) {
                entry = virtualInvokers.firstEntry();
            }

            return entry.getValue();
        }

    }


}
