package qq.infrastructure.caching;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by leo on 2017-05-26.
 */
public class AnnotationContext {

    private final static Map<Class, Map<Class, Annotation>> map = new ConcurrentHashMap<>();

    public static <T extends Annotation> T get(Class sourceClass, Class<T> annotationClass) {
        if (map.containsKey(sourceClass) == false) {
            Map<Class, Annotation> list = new ConcurrentHashMap<>();
            map.put(sourceClass, list);
        }
        Map<Class, Annotation> annotations = map.get(sourceClass);
        if (annotations.containsKey(annotationClass)) {
            return (T) annotations.get(annotationClass);
        }
        T value = (T) sourceClass.getAnnotation(annotationClass);
        if (value != null) {
            annotations.put(annotationClass, value);
        }
        return value;
    }

}
