package github.mikkeyf.extension;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-02  18:24
 */
public class Holder<T> {

    private volatile T value;

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}

