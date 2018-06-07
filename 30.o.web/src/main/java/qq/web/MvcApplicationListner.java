package qq.web;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import qq.data.WpkHibernateSessionFactory;
import qq.data.caching.DbCacheContext;
import qq.infrastructure.AppContext;
import qq.infrastructure.hibernate.DtoResultTransformer;
import qq.infrastructure.logging.LogHelper;
import qq.infrastructure.logging.MongoWpkLogAppender;
import qq.infrastructure.mongodb.MongoDatabaseWpkFactory;
import qq.infrastructure.redis.RedisWpkPoolContext;
import qq.infrastructure.security.WpkCrypto;
import qq.infrastructure.sms.WpkSmsApi;
import qq.service.AliyunOssApi;
import qq.util.extensions.KnownException;
import qq.util.helper.ClassHelper;
import qq.web.core.AreaRegistration;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

public class MvcApplicationListner implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ContextClosedEvent) {
//            RedisWpkPoolContext.getInstance().close();
//            WpkHibernateSessionFactory.getInstance().close();
//            DbCacheContext.getInstance().stop();
            LogHelper.stopLogger();
        } else if (applicationEvent instanceof ContextStartedEvent || applicationEvent instanceof ContextRefreshedEvent) {

//            Properties appProperties = getAppProperties();

            MongoDatabaseWpkFactory.getInstance().initialize();
            LogHelper.startLogger(new MongoWpkLogAppender(AppContext.getAppConfig().getLogsDbName()), true);
//            DtoResultTransformer.checkDtoEntityConstructors(ClassHelper.getClasses(AppContext.getStartupDirectory(), "qq.data.entity."));
//            WpkHibernateSessionFactory.getInstance().initialize(AppContext.getStartupDirectory());
//            WpkCrypto.getInstance().initialize();

//            this.registerAllAreas();

//            DbCacheContext.getInstance().initializeAll().start();

//            RedisWpkPoolContext.getInstance().initialize();
//            AliyunOssApi.initializeAll();
//            WpkSmsApi.getInstance().initialize(AppContext.getSmsConfig());
        }
    }

    private Properties getAppProperties() {
        Path path = Paths.get(AppContext.getStartupDirectory(), "WEB-INF", "app.properties");
        try {
            return PropertiesLoaderUtils.loadProperties(new FileSystemResource(path.toFile()));
        } catch (Exception ex) {
            throw new KnownException("加载app.properties文件失败");
        }
    }

    private void registerAllAreas() {
        Map<String, AreaRegistration> areas = AppContext.getBeans(AreaRegistration.class);
        for (String key : areas.keySet()) {
            try {
                areas.get(key).run();
            } catch (Exception ex) {
                LogHelper.log("系统-AreaRegistration", ex);
                throw new KnownException("\n\nAreaRegistration 出现严重错误\n\n");
            }
        }
    }


}
