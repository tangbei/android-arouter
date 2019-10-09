package com.tang.common.delegate;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * 描述: 代理app生命周期
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/12.
 */
public interface AppLifecycles {

    void attachBaseContext(@NonNull Context base);

    void onCreate(@NonNull Application application);

    void onTerminate(@NonNull Application application);
}
