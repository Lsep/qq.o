package qq.web;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import qq.infrastructure.redis.RedisWpkPoolContext;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

/**
 * Created by leo on 2017-06-28.
 */
public class MvcApplicationListner implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ContextClosedEvent) {
            RedisWpkPoolContext.getInstance().close();
            WpkHibernateSessionFactory.getInstance().close();
            AliyunMqApi.getInstance().close();
            DbCacheContext.getInstance().stop();
            DataPointContext.getInstance().stop();
            LogHelper.stopLogger();
        } else if (applicationEvent instanceof ContextStartedEvent || applicationEvent instanceof ContextRefreshedEvent) {

            Properties appProperties = getAppProperties();

            MongoDatabaseWpkFactory.getInstance().initialize();
            LogHelper.startLogger(new MongoWpkLogAppender(AppContext.getAppConfig().getLogsDbName()), true);
            DtoResultTransformer.checkDtoEntityConstructors(ClassHelper.getClasses(AppContext.getStartupDirectory(), "wpk.data.entities."));
            WpkHibernateSessionFactory.getInstance().initialize(AppContext.getStartupDirectory());
            WupinkuHibernateSessionFactory.getInstance().config(AppContext.getBean(OldDbConfig.class)).initialize();
            WpkCrypto.getInstance().initialize();

            this.registerAllAreas();

            DbCacheContext.getInstance().initializeAll().start();
            DataPointContext.getInstance().initialize().start();
            HtmlPage.initSystemHtmlPages();
            Image.initSystemImages();
            Tag.initPackageInTag();
            PermissionRegistery.initAllPermissions();
            //SqlRegistry.register();
            VersionRegistry.register(adminVersion, appVersion, dealerVersion);

            WxKeyManager.getInstance().initialize(new RedisWxCacheProvider());
            RedisWpkPoolContext.getInstance().initialize();
            AliyunMqApi.getInstance().initialize(AppContext.getMqConfig());
            AliyunOssApi.initializeAll();
            WpkSmsApi.getInstance().initialize(AppContext.getSmsConfig());
            SDKConfig.getConfig().loadProperties(appProperties);
            WpkUnionpayApi.getInstance().initialize(AppContext.getUnionpayConfig());
            WpkAlipayApi.getInstance().initialize(AppContext.getAlipayMobileConfig());
            WpkWeixinPayApi.getInstance().initialize(AppContext.getWeixinPayConfig());
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
