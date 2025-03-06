package github.mikkeyf.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-06  21:54
 */
@AllArgsConstructor
@Getter
public enum ServiceDiscoveryEnum {
    ZK("zk");
    private final String value;
}
