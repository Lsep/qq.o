package qq.infrastructure.aliyun.sms;

import qq.util.extensions.JList;

public interface SmsTemplate {
    JList<String> getPhoneNumbers();
    String getSignName();
    String getTemplateCode();
    String toJson();
}
