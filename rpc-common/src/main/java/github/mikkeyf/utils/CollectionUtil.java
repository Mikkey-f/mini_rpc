package github.mikkeyf.utils;

import java.util.Collection;

/**
 * 集合工具类
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-07  15:30
 */
public class CollectionUtil {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
}
