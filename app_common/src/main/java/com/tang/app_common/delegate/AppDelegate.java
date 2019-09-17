package com.tang.app_common.delegate;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: application实现
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/12.
 */
public class AppDelegate implements AppLifecycles {

    private Application mApplication;

    private List<Application.ActivityLifecycleCallbacks> mActivityLifecycles = new ArrayList<>();

    private ActivityConfig mActivityConfig;

    public AppDelegate(@NonNull Context context){
        this.mActivityConfig = new AppLifecycleImpl();
    }

    @Override
    public void attachBaseContext(@NonNull Context base) {
        mActivityConfig.injectActivityLifecycle(base,mActivityLifecycles);
    }

    @Override
    public void onCreate(@NonNull Application application) {
        this.mApplication = application;
        for (Application.ActivityLifecycleCallbacks lifecycle : mActivityLifecycles){
            mApplication.registerActivityLifecycleCallbacks(lifecycle);
        }
    }

    @Override
    public void onTerminate(@NonNull Application application) {
        if (mActivityLifecycles != null && mActivityLifecycles.size() > 0) {
            for (Application.ActivityLifecycleCallbacks lifecycle : mActivityLifecycles) {
                mApplication.unregisterActivityLifecycleCallbacks(lifecycle);
            }
        }
        this.mActivityLifecycles = null;
    }
}
