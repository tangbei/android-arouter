package com.tang.common.api;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.tang.common.utils.LogUtil;

/**
 * 描述: activity生命周期代理实现类
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/12.
 */
public class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = "aRouter--ActivityLifecycleCallbacksImpl->";

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        LogUtil.d(TAG,"onActivityCreated:"+activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        LogUtil.d(TAG,"onActivityStarted:"+activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        LogUtil.d(TAG,"onActivityResumed:"+activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        LogUtil.d(TAG,"onActivityPaused:"+activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        LogUtil.d(TAG,"onActivityStopped:"+activity);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        LogUtil.d(TAG,"onActivitySaveInstanceState:"+activity);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        LogUtil.d(TAG,"onActivityDestroyed:"+activity);
    }
}
