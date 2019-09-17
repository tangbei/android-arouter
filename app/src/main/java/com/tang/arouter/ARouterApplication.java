package com.tang.arouter;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.squareup.leakcanary.LeakCanary;
import com.tang.app_common.cookies.PersistentCookieStore;
import com.tang.app_common.delegate.AppDelegate;
import com.tang.app_common.delegate.AppLifecycles;
import com.tang.app_common.utils.ConstantUtil;

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
            mAppDelegate = new AppDelegate(base);
        this.mAppDelegate.attachBaseContext(base);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (null != mAppDelegate){
            mAppDelegate.onCreate(this);
        }
        ConstantUtil.init(this);
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
