package com.tang.common.http;

import com.tang.common.constant.AppConfig;
import com.tang.common.constant.Constant;
import com.tang.common.cookies.CookiesManager;
import com.tang.common.interceptor.HttpLoggingInterceptor;
import com.tang.common.utils.AppUtil;
import com.tang.common.utils.ConstantUtil;

import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 描述: retorfit+okhttp的请求配置
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/16.
 */
@Deprecated
public class HttpManager {

    private static final int TIME_OUT = 5 * 1000;//链接超时时间
    private volatile static HttpService httpService;
    private static File cacheFile = new File(AppConfig.Path.APP_PATH_CACHE);
    private static Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);

    public static HttpService getCommonApi() {
        try {
            if (httpService == null) {
                synchronized (HttpManager.class) {
                    if (httpService == null) {
                        httpService = createService(HttpService.class, Constant.Api.BASE_URL);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpService;
    }

    private static <S> S createService(Class<S> serviceClass, String url) throws Exception {
        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(url)
                        .client(httpClient.build())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit.create(serviceClass);
    }

    private static Interceptor cacheInterceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!AppUtil.isNetworkConnected()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            if (AppUtil.isNetworkConnected()) {
                int maxAge = 0;
                // 有网络时, 不缓存, 最大保存时长为0
                response.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .removeHeader("Pragma")
                        .build();
            } else {
                // 无网络时，设置超时为24小时
                int maxStale = 60 * 60 * 24;
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
            return response;
        }
    };

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder()
                    //设置超时
                    .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
//                    .addInterceptor(RequestInterceptor.getInstance())//网络请求 统一拦截
                    .addInterceptor(getLogInterceptor())
                    .sslSocketFactory(getSSLSocketFactory())
                    //设置缓存
                    .addNetworkInterceptor(cacheInterceptor)
                    .addInterceptor(cacheInterceptor)
                    .cache(cache)
                    //设置cookies
                    .cookieJar(new CookiesManager())
                    .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);


    private static HttpLoggingInterceptor getLogInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        if (ConstantUtil.isAppDebug()) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.ALL);
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return loggingInterceptor;
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
