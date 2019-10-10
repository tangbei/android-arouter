package com.tang.frame.other;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/16.
 */
public class ConstantUtil {

    private static Context context;

    private static String BASE_URL;

    private static String APP_PATH_GLIDE;

    /**
     * 初始化工具类
     * @param context 上下文对象
     * @param baseUrl 请求地址
     * @param path glide缓存地址
     */
    public static void init(Context context, String baseUrl, String path) {
        ConstantUtil.context = context.getApplicationContext();
        ConstantUtil.BASE_URL = baseUrl;
        ConstantUtil.APP_PATH_GLIDE = path;
    }

    /**
     * 获取ApplicationContext
     * @return ApplicationContext
     */
    public static Context getAPPContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }

    /**
     * 获取请求地址
     * @return
     */
    public static String getBaseUrl() {
        if (TextUtils.isEmpty(BASE_URL)) {
            return "";
        }
        return BASE_URL;
    }

    /**
     * 获取glide的缓存地址
     * @return
     */
    public static String getAppPathGlide() {
        if (TextUtils.isEmpty(APP_PATH_GLIDE)){
            return "";
        }
        return APP_PATH_GLIDE;
    }

    /**
     * 判断App是否是Debug版本
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppDebug() {
        String packageName = context.getPackageName();
        if (packageName == null || packageName.trim().length() == 0)
            return false;
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(), 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
