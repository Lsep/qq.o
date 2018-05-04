package qq.infrastructure.hibernate;

import qq.util.extensions.JMap;

public interface DtoEntity {
    void fill(JMap<String, Object> row);
}
