package github.mikkeyf.entity;

import lombok.*;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-01  22:07
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class RpcServiceEntity {
    /**
     * service version
     * Maybe your service will be changed
        *
     */
    private String version = "";

    /**
     * when the interface has multiple implementation classes, distinguish by group
     */
    private String group = "";

    /**
     * get the target service
     */
    private Object service;

    public String getRpcServiceName() {
        return this.getServiceName() + this.getGroup() + this.getVersion();
    }

    public String getServiceName() {
        return this.service.getClass().getInterfaces()[0].getCanonicalName();
    }
}
