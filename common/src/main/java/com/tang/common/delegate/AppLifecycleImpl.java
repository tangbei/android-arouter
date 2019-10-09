package com.tang.common.delegate;

import android.app.Application;
import android.content.Context;

import com.tang.common.api.ActivityLifecycleCallbacksImpl;

import java.util.List;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/12.
 */
public class AppLifecycleImpl implements ActivityConfig {

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> activityLifecycles) {
        activityLifecycles.add(new ActivityLifecycleCallbacksImpl());
    }
}
