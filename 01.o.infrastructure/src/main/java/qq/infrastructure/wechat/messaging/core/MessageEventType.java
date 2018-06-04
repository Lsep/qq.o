package qq.infrastructure.wechat.messaging.core;

public enum MessageEventType {
    unknown,
    /// <summary>
    /// 用户未关注，现在关注了
    /// </summary>
    subscribe ,
    /// <summary>
    /// 用户本来就已经关注了
    /// </summary>
    SCAN,
    CLICK,
    VIEW,
    unsubscribe,
    TEMPLATESENDJOBFINISH,
    LOCATION,
    MASSSENDJOBFINISH;
}
