package github.mikkeyf.loadbalance;

import github.mikkeyf.extension.SPI;
import github.mikkeyf.remoting.dto.RpcRequest;

import java.util.List;

/**
 * Use the load balancing policy for getting interface
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-08  15:38
 */
@SPI
public interface LoadBalance {

    String selectServiceAddress(List<String> serviceUrlList, RpcRequest request);
}
