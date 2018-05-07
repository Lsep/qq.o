package qq.infrastructure.security;

public class WpkCrypto extends Crypto {

    private final static WpkCrypto instance = new WpkCrypto();

    public static WpkCrypto getInstance() {
        return instance;
    }

    private WpkCrypto() {
        super("W5pK0o", "DES");
    }

}
