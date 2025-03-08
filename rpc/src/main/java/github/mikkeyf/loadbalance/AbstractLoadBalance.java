package github.mikkeyf.loadbalance;

import github.mikkeyf.remoting.dto.RpcRequest;
import github.mikkeyf.utils.CollectionUtil;

import java.util.Collection;
import java.util.List;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-08  15:46
 */
public abstract class AbstractLoadBalance implements LoadBalance {
    @Override
    public String selectServiceAddress(List<String> serviceUrlList, RpcRequest request) {
        if (CollectionUtil.isEmpty(serviceUrlList)) {
            return null;
        }
        if (serviceUrlList.size() == 1) {
            return serviceUrlList.get(0);
        }
        return doSelect(serviceUrlList, request);
    }

    protected abstract String doSelect(List<String> serviceUrlList, RpcRequest request);
}
