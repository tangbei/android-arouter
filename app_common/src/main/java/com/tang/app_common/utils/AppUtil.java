package com.tang.app_common.utils;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/16.
 */
public class AppUtil {

    /**
     * 检查是否有可用网络
     * @return
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) ConstantUtil.getAPPContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        return connectivityManager.getActiveNetworkInfo() != null;
    }

    /**
     * dp 转 px
     *
     * @param context {@link Context}
     * @param dpValue {@code dpValue}
     * @return {@code pxValue}
     */
    public static int dip2px(@NonNull Context context, float dpValue) {
        final float scale = getResources(context).getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px 转 dp
     *
     * @param context {@link Context}
     * @param pxValue {@code pxValue}
     * @return {@code dpValue}
     */
    public static int pix2dip(@NonNull Context context, int pxValue) {
        final float scale = getResources(context).getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp 转 px
     *
     * @param context {@link Context}
     * @param spValue {@code spValue}
     * @return {@code pxValue}
     */
    public static int sp2px(@NonNull Context context, float spValue) {
        final float fontScale = getResources(context).getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px 转 sp
     *
     * @param context {@link Context}
     * @param pxValue {@code pxValue}
     * @return {@code spValue}
     */
    public static int px2sp(@NonNull Context context, float pxValue) {
        final float fontScale = getResources(context).getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 获得资源
     */
    public static Resources getResources(Context context) {
        return context.getResources();
    }

    /**
     * 获取设备唯一id
     * @return
     */
    public static String getDetailId(Context context){
        String id = "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            try{
                TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
                id = tm.getDeviceId();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if (TextUtils.isEmpty(id)){
            id = Settings.Secure.getString(context.getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID);
        }
        if (TextUtils.isEmpty(id)){
            ToastUtil.show(context,"获取设备id失败");
        }
        return id;
    }
}
