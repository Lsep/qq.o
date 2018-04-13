package qq.extensions;

@FunctionalInterface
public interface Function2<T1, T2, TResult> {

    TResult apply(T1 item1, T2 item2);
}
