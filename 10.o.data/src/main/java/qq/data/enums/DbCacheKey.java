package qq.data.enums;

import qq.util.extensions.JList;

public enum DbCacheKey {
    UNKNOWN(0, "未知"),
    SETTING(1, "s_settings表"),
    WEIXIN_TEMPLATE(2, "s_weixin_templates表"),
    ROLE(3, "s_roles表");

    public static final String Doc = "0：未知；1：s_settings表；2：s_weixin_templates表；3：s_roles表";

    private int value;
    private String text;

    DbCacheKey(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return this.value;
    }

    public String getText() {
        return this.text;
    }

    public static DbCacheKey from(Integer value) {
        DbCacheKey cacheKey = JList.from(DbCacheKey.values()).firstOrNull(x -> x.getValue() == value);
        return cacheKey == null ? DbCacheKey.UNKNOWN : cacheKey;
    }
}
