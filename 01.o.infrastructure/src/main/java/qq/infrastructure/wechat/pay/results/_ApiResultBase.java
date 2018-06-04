package qq.infrastructure.wechat.pay.results;

import java.util.Map;

public abstract class _ApiResultBase {

    private String return_code;
    private String return_msg;

    public boolean isHttpSuccess(){
        return "SUCCESS".equals(this.return_code);
    }

    public _ApiResultBase loadFromResponse(Map<String, String> response){
        this.return_code = response.get("return_code");
        this.return_msg = response.get("return_msg");
        return this;
    }

    public String getReturn_code() {
        return return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }
}
