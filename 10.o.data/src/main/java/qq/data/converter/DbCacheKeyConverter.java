package qq.data.converter;

import qq.data.enums.DbCacheKey;

import javax.persistence.AttributeConverter;

public class DbCacheKeyConverter implements AttributeConverter<DbCacheKey, Integer> {
    @Override
    public Integer convertToDatabaseColumn(DbCacheKey cacheKey) {
        return cacheKey.getValue();
    }

    @Override
    public DbCacheKey convertToEntityAttribute(Integer integer) {
        return DbCacheKey.from(integer);
    }
}
