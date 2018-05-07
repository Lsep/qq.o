package qq.data.entity;

import qq.data.converter.DbCacheKeyConverter;
import qq.data.entity.core.EntityBase;
import qq.data.enums.DbCacheKey;
import qq.util.extensions.JDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "s_db_caches")
public class DbCache extends EntityBase {

    @Column(name = "cache_key", columnDefinition = "int not null COMMENT '文档：" + DbCacheKey.Doc + "'")
    @Convert(converter = DbCacheKeyConverter.class)
    private DbCacheKey cacheKey;

    @Column(name = "version", columnDefinition = "bigint not null COMMENT '版本号，以1970-1-1以来的秒数计算'")
    private long version;

    public DbCache() {

    }

    public DbCache(DbCacheKey key, long version) {
        this.cacheKey = key;
        this.version = version;
    }

    public void refreshVersion() {
        this.version = JDate.now().getVersionSeconds();
    }

    public DbCacheKey getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(DbCacheKey cacheKey) {
        this.cacheKey = cacheKey;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
