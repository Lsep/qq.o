package qq.web.security;

import qq.infrastructure.security.WpkCrypto;
import qq.util.helper.CookieHelper;
import qq.util.helper.JsonHelper;
import qq.util.helper.StringHelper;

public class WebIdentity {
    private String username;
    private String password;

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public void setUsername(String value){
        username = value;
    }

    public void setPassword(String value){
        password = value;
    }

    public WebIdentity(){

    }

    public WebIdentity(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String toEncryptedRaw(){
        return WpkCrypto.getInstance().encrypt(JsonHelper.serialize(this));
    }

    public static WebIdentity parseFromRequest(){
        String cookie = CookieHelper.getValue("");//todo auth
        if(cookie == null || StringHelper.isNullOrWhitespace(cookie)){
            return null;
        }
        String decodedValue = WpkCrypto.getInstance().decrypt(cookie);
        return JsonHelper.deserialize(decodedValue, WebIdentity.class);
    }


}
