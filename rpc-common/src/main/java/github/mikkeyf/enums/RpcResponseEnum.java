package github.mikkeyf.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-07  14:10
 */
@AllArgsConstructor
@Getter
@ToString
public enum RpcResponseEnum {
    SUCCESS(200, "The remote call is successful"),
    FAIL(500, "The remote call is fail");
    private final int code;
    private final String message;
}
