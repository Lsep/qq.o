package qq.extensions;

@FunctionalInterface
public interface Action1<T> {
    void apply(T item);
}
