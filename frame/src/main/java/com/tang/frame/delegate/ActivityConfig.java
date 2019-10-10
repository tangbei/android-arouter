package com.tang.frame.delegate;

import android.app.Application;
import android.content.Context;

import java.util.List;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/12.
 */
public interface ActivityConfig {

    void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> activityLifecycles);
}
