package qq.util.extensions;

@FunctionalInterface
public interface ThrowableAction {
    void apply() throws Throwable;
}
