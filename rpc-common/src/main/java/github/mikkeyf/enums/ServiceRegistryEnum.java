package github.mikkeyf.enums;

/**
*@Author: Mikkeyf
*@CreateTime: 2025-03-02  18:39
*/

import lombok.Getter;

@Getter
public enum ServiceRegistryEnum {
    ZK("zkImpl");
    String name;

    ServiceRegistryEnum(String name) {
        this.name = name;
    }
}
