package qq.extensions;

@FunctionalInterface
public interface ThrowableFunction2<T1, T2, TResult> {

    TResult apply(T1 item1, T2 item2) throws Throwable;
}
