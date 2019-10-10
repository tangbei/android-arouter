package com.tang.frame.manager;

import com.tang.frame.cache.Cache;

import io.rx_cache2.internal.RxCache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * 描述: retrofit请求配置
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/12.
 */
public interface IRequestManager {

    /**
     * retrofit配置
     * @return
     */
    Retrofit provideRetorfit();

    /**
     * okhttp配置
     * @return
     */
    OkHttpClient provideClient();

    /**
     * rx缓存配置
     */
    RxCache provideRxCache();

    /**
     * 缓存
     * @return
     */
    Cache.Factory provideFoctory();

}
