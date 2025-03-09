package github.mikkeyf.utils;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-09  13:17
 */
public class RuntimeUtil {
    /**
     * 获取CPU的核心数
     * @return cpu的核心数
     */
    public static int getCpus() {
        return Runtime.getRuntime().availableProcessors();
    }
}
