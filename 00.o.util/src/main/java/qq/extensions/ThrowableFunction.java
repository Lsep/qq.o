package qq.extensions;

@FunctionalInterface
public interface ThrowableFunction<T, TResult> {

    TResult apply(T item) throws Throwable;
}
