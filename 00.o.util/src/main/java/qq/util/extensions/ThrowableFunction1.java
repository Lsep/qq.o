package qq.util.extensions;

@FunctionalInterface
public interface ThrowableFunction1<TResult> {
    TResult apply() throws Throwable;
}
