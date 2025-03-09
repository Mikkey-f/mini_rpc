package github.mikkeyf.serialize;

import github.mikkeyf.extension.SPI;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-09  14:17
 */
@SPI
public interface Serializer {

    /**
     * 反序列化
     * @param obj need serialize object
     * @return byte array
     */
    byte[] serialize(Object obj);

    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
