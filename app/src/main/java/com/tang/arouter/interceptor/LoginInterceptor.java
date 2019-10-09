package com.tang.arouter.interceptor;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.tang.common.utils.LogUtil;

/**
 * 描述: 登录拦截
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/17.
 */
@Interceptor(priority = 8, name = "登录拦截器")
public class LoginInterceptor implements IInterceptor {

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        LogUtil.d("tang--->","我是登录拦截器哦");
        callback.onContinue(postcard);  // 处理完成，交还控制权
    }

    @Override
    public void init(Context context) {
        LogUtil.d("tang--->","我是登录拦截器哦init");
    }
}
