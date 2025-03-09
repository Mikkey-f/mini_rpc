package github.mikkeyf.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-09  15:11
 */
@AllArgsConstructor
@Getter
public enum CompressTypeEnum {
    GZIP((byte) 0x01, "gzip");

    private final byte code;
    private final String name;

    public static String getName(byte code) {
        for (CompressTypeEnum c : CompressTypeEnum.values()) {
            if (c.getCode() == code) {
                return c.name;
            }
        }
        return null;
    }
}
