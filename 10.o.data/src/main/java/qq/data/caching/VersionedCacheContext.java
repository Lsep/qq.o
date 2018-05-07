package qq.data.caching;

import qq.data.enums.DbCacheKey;

public interface VersionedCacheContext {
    long getVersion();

    void refresh(long version);

    void initialize(long version);

    DbCacheKey getCacheKey();
}
