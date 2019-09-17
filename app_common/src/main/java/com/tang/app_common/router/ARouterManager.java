package com.tang.app_common.router;

import android.app.Activity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.tang.app_common.R;
import com.tang.app_common.constant.Constant;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/17.
 */
public class ARouterManager {

    /**
     * 不带参数的跳转
     * @param url
     */
    public static void startActivity(String url){
        ARouter.getInstance()
                .build(url)
                .navigation();
    }

    /**
     * 不带参数的跳转
     * @param url
     */
    public static void startActivity(Activity activity,String url){
        ARouter.getInstance()
                .build(url)
                .withTransition(startTransition(0,Constant.AnimationType.LEFT),startTransition(1,Constant.AnimationType.LEFT))
                .navigation(activity);
    }

    /**
     * 带参数跳转
     * @param url 跳转的路由地址
     * @param bundle 携带的参数
     */
    public static void startActivity(String url, Bundle bundle){
        // 对象传递
        ARouter.getInstance()
                .build(url)
                .with(bundle)
                .navigation();
    }

    /**
     * 带参数跳转
     * @param url 跳转的路由地址
     * @param bundle 携带的参数
     */
    public static void startActivity(Activity activity,String url, Bundle bundle){
        // 对象传递
        ARouter.getInstance()
                .build(url)
                .with(bundle)
                .withTransition(startTransition(0,Constant.AnimationType.LEFT),startTransition(1,Constant.AnimationType.LEFT))
                .navigation(activity);
    }

    /**
     * activity跳转回调
     * @param activity 当前activity对象
     * @param url 跳转路由
     * @param bundle 跳转参数
     * @param requstCode 回调code
     */
    public static void startActivityForResult(Activity activity, String url, Bundle bundle, int requstCode){
        // 对象传递
        ARouter.getInstance()
                .build(url)
                .with(bundle)
                .withTransition(startTransition(0,Constant.AnimationType.LEFT),startTransition(1,Constant.AnimationType.LEFT))
                .navigation(activity,requstCode);
    }

    /**
     * activity跳转查询结果
     * @param activity 当前activity对象
     * @param url 跳转路由
     * @param bundle 跳转参数
     * @param callback 结果回调
     */
    public static void startActivityCallback(Activity activity,String url, Bundle bundle, NavigationCallback callback){
        // 对象传递
        ARouter.getInstance()
                .build(url)
                .with(bundle)
                .withTransition(startTransition(0,Constant.AnimationType.LEFT),startTransition(1,Constant.AnimationType.LEFT))
                .navigation(activity,callback);
    }

    /**
     * 通过所有拦截器
     * @param url 跳转路由
     * @param bundle 跳转参数
     */
    public static void startActivityGreen(Activity activity,String url, Bundle bundle){
        // 对象传递
        ARouter.getInstance()
                .build(url)
                .with(bundle)
                .greenChannel()
                .withTransition(startTransition(0,Constant.AnimationType.LEFT),startTransition(1,Constant.AnimationType.LEFT))
                .navigation(activity);
    }

    /**
     * 跳转动画
     * @param direct 0 = 左，1 = 右
     * @param animType 跳转类型
     * @return
     */
    private static int startTransition(int direct,int animType) {
        switch (animType){
            case Constant.AnimationType.LEFT:
                return direct == 0 ? R.anim.push_right_in : R.anim.push_left_out;
            case Constant.AnimationType.RIGHT:
                return direct == 0 ? R.anim.push_left_out : R.anim.push_right_in;
            case Constant.AnimationType.BOTTOM:
                return direct == 0 ? R.anim.push_bottom_in : R.anim.push_top_out;
            case Constant.AnimationType.SHARE_ELEMENTS:
                return direct == 0 ? 0 : R.anim.push_top_out;
            default:
                return direct == 0 ? 0 : 0;
        }
    }
}
