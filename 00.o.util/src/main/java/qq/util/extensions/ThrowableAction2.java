package qq.util.extensions;

@FunctionalInterface
public interface ThrowableAction2<T1, T2> {
    void apply(T1 item1, T2 item2) throws Throwable;
}
