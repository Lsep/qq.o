package qq.extensions;

@FunctionalInterface
public interface ThrowableAction {
    void apply() throws Throwable;
}
