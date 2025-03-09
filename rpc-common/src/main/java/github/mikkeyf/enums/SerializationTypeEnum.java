package github.mikkeyf.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-09  14:20
 */
@AllArgsConstructor
@Getter
public enum SerializationTypeEnum {
    KYRO((byte) 0x01, "kyro"),
    PROTOSTUFF((byte) 0x02, "protostuff"),
    HESSIAN((byte) 0x03, "hessian");

    private final byte code;
    private final String name;

    public static String getName(byte code) {
        for (SerializationTypeEnum c : SerializationTypeEnum.values()) {
            if (c.getCode() == code) {
                return c.name;
            }
        }
        return null;
    }
}
