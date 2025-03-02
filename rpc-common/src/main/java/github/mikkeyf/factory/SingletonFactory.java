package github.mikkeyf.factory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Singleton Factory class: to get Singleton Object
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-02  17:07
 */
public class SingletonFactory {
    private static final Map<String, Object> OBJECT_MAP = new ConcurrentHashMap<>();
    private static final Object LOCK = new Object();

    private SingletonFactory() {
    }

    public static <T> T getSingleton(Class<T> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        String key = c.toString();
        if (OBJECT_MAP.containsKey(key)) {
            Object target = OBJECT_MAP.get(key);
            return c.cast(target);
        } else {
            synchronized (LOCK) {
                if (!OBJECT_MAP.containsKey(key)) {
                    try {
                        T instance = c.getDeclaredConstructor().newInstance();
                        OBJECT_MAP.put(key, instance);
                        return instance;
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        throw new RuntimeException(e.getMessage(), e);
                    }
                } else {
                    return c.cast(OBJECT_MAP.get(key));
                }
            }
        }
    }
}
