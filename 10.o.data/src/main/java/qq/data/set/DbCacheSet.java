package qq.data.set;

import qq.data.entity.DbCache;
import qq.data.enums.DbCacheKey;
import qq.infrastructure.hibernate.DbContext;
import qq.infrastructure.hibernate.DbSet;
import qq.infrastructure.logging.LogHelper;
import qq.util.extensions.JDate;

public class DbCacheSet extends DbSet<DbCache> {
    public DbCacheSet(DbContext db) {
        super(db, DbCache.class);
    }


    public void refresh(DbCacheKey key) {
        DbCache cache = super.getFirst("where cache_key=:p0", key.getValue());
        if (cache == null) {
            LogHelper.log("严重错误dbcache", key.toString());
            return;
        }
        cache.setVersion(JDate.now().getVersionSeconds());
        db.save(cache);
    }
}
