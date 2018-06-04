package qq.infrastructure.wechat.messaging;


import qq.infrastructure.wechat.messaging.core.MessageBase;
import qq.infrastructure.wechat.messaging.core.NewsMessageItem;
import qq.util.helper.JsonHelper;

import java.util.ArrayList;
import java.util.List;

public class NewsListMessage extends MessageBase {
    private final List<NewsMessageItem> items;

    public NewsListMessage(String openId) {
        super(openId);
        this.items = new ArrayList<>();
    }

    public void add(NewsMessageItem item) {
        this.items.add(item);
    }


    @Override
    public String toJson() {
        JsonDto dto = new JsonDto();
        dto.touser = this.getToUserOpenId();
        dto.news.articles = this.items;
        return JsonHelper.serialize(dto);
    }

    public List<NewsMessageItem> getItems() {
        return items;
    }


    private class JsonDto {
        private String touser;
        private String msgtype = "news";
        private JsonDtoArticles news = new JsonDtoArticles();

        public String getTouser() {
            return touser;
        }

        public String getMsgtype() {
            return msgtype;
        }
    }

    private class JsonDtoArticles {
        private List<NewsMessageItem> articles;

        public List<NewsMessageItem> getArticles() {
            return articles;
        }
    }

}
