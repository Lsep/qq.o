package qq.extensions;

public class KnownException extends RuntimeException {
    private String code;

    public String getCode() {
        return this.code;
    }

    public KnownException(String message) {
        super(message);
    }

    public KnownException(String message, String code) {
        super(message);
        this.code = code;
    }
}
