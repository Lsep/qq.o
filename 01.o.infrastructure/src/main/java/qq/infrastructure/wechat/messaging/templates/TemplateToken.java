package qq.infrastructure.wechat.messaging.templates;

import qq.util.extensions.Function1;

public class TemplateToken {
    private String key;
    private String description;
    private Function1<String> getValue;

    public TemplateToken(){

    }

    public TemplateToken(String key, String description, Function1<String> getValue){
        this.key = key;
        this.description = description;
        this.getValue = getValue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Function1<String> getGetValue() {
        return getValue;
    }
}
