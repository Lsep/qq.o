package qq.infrastructure.hibernate;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.transform.ResultTransformer;
import qq.infrastructure.logging.LogHelper;
import qq.util.extensions.JList;
import qq.util.extensions.JMap;

import java.util.List;

public class DtoResultTransformer implements ResultTransformer {

    private final Class clazz;

    public DtoResultTransformer(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        try {
            DtoEntity entity = (DtoEntity) (this.clazz.newInstance());
            JMap<String, Object> map = new JMap<>();
            for (int i = 0; i < aliases.length; i++) {
                map.put(StringUtils.lowerCase(aliases[i]), tuple[i]);
            }
            entity.fill(map);
            return entity;
        } catch (Exception ex) {
            LogHelper.log("_transformTuple." + this.clazz.getName(), ex);
            return null;
        }
    }

    @Override
    public List transformList(List collection) {
        return collection;
    }

    public static void checkDtoEntityConstructors(JList<Class> dtoClasses) {
        for (Class clazz : dtoClasses) {
            Class[] interfaces = clazz.getInterfaces();
            for (Class interfaceClass : interfaces) {
                if (interfaceClass.equals(DtoEntity.class)) {
                    try {
                        clazz.newInstance();
                    } catch (Exception ex) {
                        LogHelper.log("_checkDtoEntityConstructors", "检查DtoEntity的实现类是否具有无参构造函数，失败：" + clazz.toString());
                    }
                }
            }
        }
        System.out.println("检查DtoEntity的实现类是否具有无参构造函数，通过\n\n");
    }

}
