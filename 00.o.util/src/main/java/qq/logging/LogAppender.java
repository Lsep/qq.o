package qq.logging;

public interface LogAppender {
    void entry(String group, String message);
    void start();
    void stop();
    boolean isStarted();
    boolean printStackTrace();
    String getServerName();
}
