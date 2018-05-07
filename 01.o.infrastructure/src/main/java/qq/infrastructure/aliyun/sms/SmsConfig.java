package qq.infrastructure.aliyun.sms;


import qq.infrastructure.aliyun.AliyunAccountConfig;

public interface SmsConfig extends AliyunAccountConfig {
    public static final String PRODUCT = "Dysmsapi";
    public static final String DOMAIN = "dysmsapi.aliyuncs.com";
    public static final String REGION_ID = "cn-hangzhou";
    public static final String ENDPOINT_NAME = "cn-hangzhou";


}
