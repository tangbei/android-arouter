package com.tang.common.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.tang.common.constant.Constant;
import com.tang.frame.other.LogUtil;

/**
 * 描述: 屏幕适配的工具类
 * 使用的是今日头条的适配方案
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/11/26.
 */
public class ScreenAdaptUtil {

    private static float normalDensity;//原本的density

    private static float normalScaledDensity;//原本的字体scaledDensity

    private static DisplayMetrics appDisplayMetrics;

    private static float appDensity;

    private static float appScaledDensity;

    private static int screenWidth;

    /**
     * 今日头条适配方案
     * @param application
     * @param activity
     */
    public static void setCustomDensity(@NonNull final Application application, @NonNull Activity activity){
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if (0 == normalDensity){
            normalDensity = appDisplayMetrics.density;
            normalScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    //监听系统字体是否有变化，更改原来的scaledDensity
                    if (null != newConfig && newConfig.fontScale > 0){
                        normalScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }

        final float targetDensity = appDisplayMetrics.widthPixels * 1.0f / Constant.Common.CREEN_WIDTH;
        final float targetScaledDensity = targetDensity * (normalScaledDensity / normalDensity);
        final int targetDensityDpi = (int) (160 * targetDensity);

        LogUtil.d("screen_adapt--->"+"density:"+targetDensity+",scaledDensity:"+targetScaledDensity+",densityDpi:"+targetDensityDpi);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }

    /**
     * 屏幕适配初始化
     * @param application
     */
    public static void setDensity(@NonNull final Application application) {
        appDisplayMetrics = application.getResources().getDisplayMetrics();

//        screenWidth = getScreenWidth(application);
//        registerActivityLifecycleCallbacks(application);

        if (appDensity == 0) {
            //初始化的时候赋值
            appDensity = appDisplayMetrics.density;
            appScaledDensity = appDisplayMetrics.scaledDensity;

            //添加字体变化的监听
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    //字体改变后,将appScaledDensity重新赋值
                    if (newConfig != null && newConfig.fontScale > 0) {
                        appScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {
                }
            });
        }
    }

    /**
     * 以宽为适配标准
     * @param activity
     */
    public static void setDefault(Activity activity) {
        setAppOrientation(activity,true);
    }

    /**
     * 以高为适配标准
     * @param activity
     */
    public static void setDefaultHeight(Activity activity){
        setAppOrientation(activity,false);
    }

    /**
     * 屏幕适配
     * @param activity
     * @param isBaseWidth 为true，则表示是以宽为标准适配，为false,则表示以高为标准适配
     */
    private static void setAppOrientation(@Nullable Activity activity, boolean isBaseWidth) {

        float targetDensity = 0;
        try {
            if (isBaseWidth){
                targetDensity = appDisplayMetrics.widthPixels * 1.0f / Constant.Common.CREEN_WIDTH;
            }else {
                targetDensity = appDisplayMetrics.heightPixels * 1.0f / Constant.Common.SCREEN_HEIGHT;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        float targetScaledDensity = targetDensity * (appScaledDensity / appDensity);
        int targetDensityDpi = (int) (160 * targetDensity);

        /**
         *
         * 最后在这里将修改过后的值赋给系统参数
         *
         * 只修改Activity的density值
         */
        DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }

    private static void registerActivityLifecycleCallbacks(Application application) {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                setDefault(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }
            @Override
            public void onActivityResumed(Activity activity) {
            }
            @Override
            public void onActivityPaused(Activity activity) {
            }
            @Override
            public void onActivityStopped(Activity activity) {
            }
            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }
            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }


    public static int getScreenWidth(Context context){
        return getScreenSize(context)[0];
    }

    /**
     * 获取当前的屏幕尺寸
     *
     * @param context {@link Context}
     * @return 屏幕尺寸
     */
    public static int[] getScreenSize(Context context) {
        int[] size = new int[2];

        WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = w.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);

        size[0] = metrics.widthPixels;
        size[1] = metrics.heightPixels;
        return size;
    }
}
