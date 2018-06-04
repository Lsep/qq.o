package qq.infrastructure.wechat.pay;

import qq.util.helper.StringHelper;

import java.util.HashMap;
import java.util.Map;

public class WxPayHelper {

    public static Map<String, String> filterEmptyValues(Map<String, String> map) {
        Map<String, String> result = new HashMap<>();
        for (String key : map.keySet()) {
            String value = map.get(key);
            if (!StringHelper.isNullOrWhitespace(value)) {
                result.put(key, value);
            }
        }
        return result;
    }

}
