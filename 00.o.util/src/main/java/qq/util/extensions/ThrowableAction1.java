package qq.util.extensions;

@FunctionalInterface
public interface ThrowableAction1<T> {
    void apply(T item) throws Throwable;
}
