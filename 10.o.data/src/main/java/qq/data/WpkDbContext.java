package qq.data;

import qq.data.set.DbCacheSet;
import qq.infrastructure.hibernate.DbContext;

public class WpkDbContext extends DbContext {
    private DbCacheSet dbCacheSet;

    public WpkDbContext() {
        super(WpkHibernateSessionFactory.getInstance());
    }

    public WpkDbContext(boolean transactional) {
        super(WpkHibernateSessionFactory.getInstance(), transactional);
    }

    public DbCacheSet getDbCacheSet() {
        if (this.dbCacheSet == null) {
            this.dbCacheSet = new DbCacheSet(this);
        }
        return dbCacheSet;
    }
}
