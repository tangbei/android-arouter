package com.tang.app_common.cookies;

import android.content.Context;

import com.tang.app_common.utils.LogUtil;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 描述: 设置cookies
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/16.
 */
public class SetCookiesInterceptor implements Interceptor {

    private Context mContext;

    public SetCookiesInterceptor(Context context) {
        this.mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        /*HashSet<String> preferences = UserUtil.cookie(mContext);
        if (preferences != null) {
            for (String cookie : preferences) {
                builder.addHeader("Cookie", cookie);
                LogUtil.d("OkHttp", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
            }
        }*/
        return chain.proceed(builder.build());
    }
}
