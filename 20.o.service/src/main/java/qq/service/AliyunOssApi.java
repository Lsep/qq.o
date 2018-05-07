package qq.service;

import qq.infrastructure.AppContext;
import qq.infrastructure.aliyun.oss.OssBucket;

public class AliyunOssApi {
    private static OssBucket images;
    private static OssBucket internal;
    private static OssBucket publik;


    public static OssBucket getImages(){
        return images;
    }

    public static OssBucket getPublic(){
        return publik;
    }

    public static OssBucket getInternal(){
        return internal;
    }

    public static void initializeAll(){
        images = new OssBucket().initialize(AppContext.getOssImagesConfig());
        publik = new OssBucket().initialize(AppContext.getOssPublicConfig());
        internal = new OssBucket().initialize(AppContext.getOssInternalConfig());
    }

    public static void closeAll(){
        images.close();
        publik.close();
        internal.close();
    }


}
