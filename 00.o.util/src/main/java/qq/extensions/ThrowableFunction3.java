package qq.extensions;

@FunctionalInterface
public interface ThrowableFunction3<T1, T2, T3, TResult> {

    TResult apply(T1 item1, T2 item2, T3 item3) throws Throwable;
}
