package qq.infrastructure.sms;

import qq.infrastructure.aliyun.sms.AliyunSmsApi;
import qq.infrastructure.sms.templates.GeneralCaptchaSmsTemplate;

public class WpkSmsApi extends AliyunSmsApi {

    private static final WpkSmsApi instance = new WpkSmsApi();

    public static final WpkSmsApi getInstance() {
        return instance;
    }

    private WpkSmsApi() {

    }

    public void sendGenralCaptcha(String phone, String code) {
        GeneralCaptchaSmsTemplate template = new GeneralCaptchaSmsTemplate(phone, code);
        super.trySend(template);
    }

}
