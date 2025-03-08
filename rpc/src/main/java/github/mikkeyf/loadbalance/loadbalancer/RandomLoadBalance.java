package github.mikkeyf.loadbalance.loadbalancer;

import github.mikkeyf.loadbalance.AbstractLoadBalance;
import github.mikkeyf.loadbalance.LoadBalance;
import github.mikkeyf.remoting.dto.RpcRequest;

import java.util.List;
import java.util.Random;

/**
 * Implementation load balance with random
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-08  15:40
 */
public class RandomLoadBalance extends AbstractLoadBalance {

    @Override
    protected String doSelect(List<String> serviceUrlList, RpcRequest request) {
        Random random = new Random();
        return serviceUrlList.get(random.nextInt(serviceUrlList.size()));
    }
}
