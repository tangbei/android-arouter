package com.tang.arouter;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.squareup.leakcanary.LeakCanary;
import com.tang.common.api.ActivityLifecycleCallbacksImpl;
import com.tang.common.constant.AppConfig;
import com.tang.common.constant.Constant;
import com.tang.frame.delegate.AppDelegate;
import com.tang.frame.delegate.AppLifecycles;
import com.tang.frame.other.ConstantUtil;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/11.
 */
public class ARouterApplication extends Application {

    private AppLifecycles mAppDelegate;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (null == mAppDelegate)
            mAppDelegate = new AppDelegate(base,new ActivityLifecycleCallbacksImpl());
        this.mAppDelegate.attachBaseContext(base);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (null != mAppDelegate){
            mAppDelegate.onCreate(this);
        }
        ConstantUtil.init(this, Constant.Api.BASE_URL, AppConfig.Path.APP_PATH_GLIDE);
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this); //初始化aRouter

        LeakCanary.install(this);
    }

    /**
     * 在模拟环境中程序终止时会被调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppDelegate != null)
            this.mAppDelegate.onTerminate(this);
    }
}
