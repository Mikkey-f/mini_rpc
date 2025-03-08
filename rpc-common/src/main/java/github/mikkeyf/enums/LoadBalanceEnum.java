package github.mikkeyf.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-08  15:49
 */
@AllArgsConstructor
@Getter
public enum LoadBalanceEnum {
    LOADBALANCE("loadBalance");

    private final String name;
}
