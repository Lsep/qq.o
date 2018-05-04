package qq.infrastructure.redis;

import qq.util.extensions.JList;
import qq.util.helper.JsonHelper;
import qq.util.helper.StringHelper;

import java.util.Objects;

public abstract class AbstractRedisListCacheReader<T> extends RedisCacheReaderBase<T> {

    protected abstract JList<T> internalReadFromDb();

    public AbstractRedisListCacheReader(Class<T> clazz) {
        super(clazz);
    }

    @Override
    public JList<T> read() {
        String value = super.getRedisValue();
        if (!StringHelper.isNullOrWhitespace(value) && !Objects.equals(value, "[]")) {
            return JsonHelper.deserializeList(value, this.getClazz());
        }
        return (JList<T>) super.getFromDbAndSetCache();
    }

    @Override
    protected JList<T> readFromDb() {
        return this.internalReadFromDb();
    }


}
