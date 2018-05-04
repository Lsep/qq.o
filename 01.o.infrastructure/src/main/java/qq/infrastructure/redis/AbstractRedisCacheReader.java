package qq.infrastructure.redis;

import qq.util.helper.JsonHelper;
import qq.util.helper.StringHelper;

public abstract class AbstractRedisCacheReader<T> extends RedisCacheReaderBase<T> {

    protected abstract T internalReadFromDb();

    public AbstractRedisCacheReader(Class<T> clazz) {
        super(clazz);
    }

    @Override
    public T read() {
        String value = super.getRedisValue();
        if (!StringHelper.isNullOrWhitespace(value)) {
            return JsonHelper.deserialize(value, this.getClazz());
        }
        return (T) super.getFromDbAndSetCache();
    }

    @Override
    protected T readFromDb() {
        return this.internalReadFromDb();
    }

}
