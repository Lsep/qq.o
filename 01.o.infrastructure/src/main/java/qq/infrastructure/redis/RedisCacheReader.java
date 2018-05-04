package qq.infrastructure.redis;

/**
 * redis缓存
 */
public interface RedisCacheReader {
    /**
     * 从redis中读取数据
     *
     * @return
     */
    Object read();

    /**
     * 刷新redis中的数据
     */
    void refresh();

    /**
     * 清除redis中的数据
     */
    void clear();
}
