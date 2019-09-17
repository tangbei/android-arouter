package com.tang.app_common.manager;

import com.google.gson.Gson;
import com.tang.app_common.cache.Cache;
import com.tang.app_common.cache.CacheType;
import com.tang.app_common.cache.IntelligentCache;
import com.tang.app_common.cache.LruCache;
import com.tang.app_common.constant.AppConfig;
import com.tang.app_common.constant.Constant;
import com.tang.app_common.cookies.ReceivedCookiesInterceptor;
import com.tang.app_common.cookies.SetCookiesInterceptor;
import com.tang.app_common.interceptor.GlobalHttpHandlerImpl;
import com.tang.app_common.interceptor.HttpLoggingInterceptor;
import com.tang.app_common.utils.ConstantUtil;
import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 描述: retrofit请求配置实现类
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/16.
 */
public class RequestManager implements IRequestManager {

    private static final int TIME_OUT = 6*1000;

    private GlobalHttpHandlerImpl globalHttpHandler;

    public RequestManager() {
        globalHttpHandler = new GlobalHttpHandlerImpl(ConstantUtil.getAPPContext());
    }

    /**
     * 配置retrofit
     * @return
     */
    @Override
    public Retrofit provideRetorfit() {
        Retrofit.Builder builder = new Retrofit.Builder().
                baseUrl(Constant.Api.BASE_URL)
                .client(provideClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用rxjava
                .addConverterFactory(GsonConverterFactory.create());//使用gson
        return builder.build();
    }

    /**
     * 配置okhttp
     * @return
     */
    @Override
    public OkHttpClient provideClient() {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT,TimeUnit.SECONDS)
                .addNetworkInterceptor(new HttpLoggingInterceptor())
                .addInterceptor(new ReceivedCookiesInterceptor(ConstantUtil.getAPPContext()))
                .addInterceptor(new SetCookiesInterceptor(ConstantUtil.getAPPContext()))
                .sslSocketFactory(getSSLSocketFactory())
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        return chain.proceed(globalHttpHandler.onHttpRequestBefore(chain,chain.request()));
                    }
                });
        return okHttpBuilder.build();
    }

    @Override
    public RxCache provideRxCache() {
        RxCache.Builder builder = new RxCache.Builder();
        builder.useExpiredDataIfLoaderNotAvailable(true);
        return builder.persistence(getCacheFile(),new GsonSpeaker(new Gson()));
    }

    @Override
    public Cache.Factory provideFoctory() {
        return (CacheType type) -> {
            //若想自定义 LruCache 的 size, 或者不想使用 LruCache, 想使用自己自定义的策略
            //使用 GlobalConfigModule.Builder#cacheFactory() 即可扩展
            switch (type.getCacheTypeId()) {
                //Activity、Fragment 以及 Extras 使用 IntelligentCache (具有 LruCache 和 可永久存储数据的 Map)
                case CacheType.EXTRAS_TYPE_ID:
                case CacheType.ACTIVITY_CACHE_TYPE_ID:
                case CacheType.FRAGMENT_CACHE_TYPE_ID:
                    return new IntelligentCache(type.calculateCacheSize(ConstantUtil.getAPPContext()));
                //其余使用 LruCache (当达到最大容量时可根据 LRU 算法抛弃不合规数据)
                default:
                    return new LruCache(type.calculateCacheSize(ConstantUtil.getAPPContext()));
            }
        };
    }

    /**
     * 创建缓存文件
     * @return
     */
    public File getCacheFile(){
        File file = new File(ConstantUtil.getAPPContext().getCacheDir(),AppConfig.Path.APP_PATH_GLIDE);
        if (!file.exists()){
            file.mkdirs();
        }
        return file;
    }

    /**
     * 不验证证书
     *
     * @return
     * @throws Exception
     */
    private static SSLSocketFactory getSSLSocketFactory() {
        //创建一个不验证证书链的证书信任管理器。
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[0];
            }
        }};

        final SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            return sslContext
                    .getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
