package qq.infrastructure.sms.templates;

import qq.infrastructure.AppContext;
import qq.infrastructure.aliyun.sms.SmsTemplate;
import qq.util.extensions.JList;
import qq.util.helper.JsonHelper;

/**
 * Created by Leo on 2017/11/13.
 模版类型:验证码
 模版名称:五品库
 模版CODE:SMS_109505474
 模版内容:您的验证码为：${code}，该验证码2分钟内有效，请勿泄露于他人。
 申请说明:用于用户注册时的验证。
 */
public class GeneralCaptchaSmsTemplate implements SmsTemplate {

    private String phone;
    private JsonDto jsonDto = new JsonDto();

    public GeneralCaptchaSmsTemplate(String phone, String code){
        this.phone = phone;
        this.jsonDto.code = code;
    }

    @Override
    public JList<String> getPhoneNumbers() {
        return JList.from(new String[]{this.phone});
    }

    @Override
    public String getSignName() {
        return AppContext.getSmsConfig().getSignName();
    }

    @Override
    public String getTemplateCode() {
        return AppContext.getSmsConfig().getIdGeneralCaptcha();
    }

    @Override
    public String toJson() {
        return JsonHelper.serialize(this.jsonDto);
    }

    private class JsonDto{
        private String code;

        public String getCode() {
            return code;
        }
    }
}
