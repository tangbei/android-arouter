package com.tang.common.manager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.tang.common.cache.Cache;
import com.tang.common.cache.CacheType;
import com.tang.common.utils.Preconditions;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.rx_cache2.internal.RxCache;
import retrofit2.Retrofit;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/16.
 */
public class RepositoryManager extends RequestManager implements IRepositoryManager {

    private Retrofit mRetrofit;
    private RxCache mRxCache;
    private Cache.Factory mCacheFactory;
    private Cache<String, Object> mRetrofitServiceCache;
    private Cache<String, Object> mCacheServiceCache;

    private volatile static RepositoryManager repositoryManager;

    private RepositoryManager(){
        mRetrofit = provideRetorfit();
        mRxCache = provideRxCache();
        mCacheFactory = provideFoctory();
    }

    public static RepositoryManager getInstance() {
        if (repositoryManager == null) {
            synchronized (RepositoryManager.class) {
                if (repositoryManager == null) {
                    repositoryManager = new RepositoryManager();
                }
            }
        }
        return repositoryManager;
    }

    @NonNull
    @Override
    public synchronized <T> T obtainRetrofitService(@NonNull Class<T> service) {
        return createWrapperService(service);
    }

    @NonNull
    @Override
    public synchronized <T> T obtainCacheService(@NonNull Class<T> cache) {
        Preconditions.checkNotNull(cache, "cacheClass == null");
        if (mCacheServiceCache == null) {
            mCacheServiceCache = mCacheFactory.build(CacheType.CACHE_SERVICE_CACHE);
        }
        Preconditions.checkNotNull(mCacheServiceCache,
                "Cannot return null from a Cache.Factory#build(int) method");
        T cacheService = (T) mCacheServiceCache.get(cache.getCanonicalName());
        if (cacheService == null) {
            cacheService = mRxCache.using(cache);
            mCacheServiceCache.put(cache.getCanonicalName(), cacheService);
        }
        return cacheService;
    }

    /**
     * 根据 https://zhuanlan.zhihu.com/p/40097338 对 Retrofit 进行的优化
     *
     * @param serviceClass ApiService class
     * @param <T> ApiService class
     * @return ApiService
     */
    private <T> T createWrapperService(Class<T> serviceClass) {
        Preconditions.checkNotNull(serviceClass, "serviceClass == null");
        // 二次代理
        return (T) Proxy.newProxyInstance(serviceClass.getClassLoader(),
                new Class<?>[]{serviceClass}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, @Nullable Object[] args)
                            throws Throwable {
                        // 此处在调用 serviceClass 中的方法时触发

                        if (method.getReturnType() == Observable.class) {
                            // 如果方法返回值是 Observable 的话，则包一层再返回，
                            // 只包一层 defer 由外部去控制耗时方法以及网络请求所处线程，
                            // 如此对原项目的影响为 0，且更可控。
                            return Observable.defer(() -> {
                                final T service = getRetrofitService(serviceClass);
                                // 执行真正的 Retrofit 动态代理的方法
                                return ((Observable) getRetrofitMethod(service, method).invoke(service, args));
                            });
                        } else if (method.getReturnType() == Single.class) {
                            // 如果方法返回值是 Single 的话，则包一层再返回。
                            return Single.defer(() -> {
                                final T service = getRetrofitService(serviceClass);
                                // 执行真正的 Retrofit 动态代理的方法
                                return ((Single) getRetrofitMethod(service, method)
                                        .invoke(service, args));
                            });
                        }

                        // 返回值不是 Observable 或 Single 的话不处理。
                        final T service = getRetrofitService(serviceClass);
                        return getRetrofitMethod(service, method).invoke(service, args);
                    }
                });
    }

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param serviceClass ApiService class
     * @param <T> ApiService class
     * @return ApiService
     */
    private <T> T getRetrofitService(Class<T> serviceClass) {
        if (mRetrofitServiceCache == null) {
            mRetrofitServiceCache = mCacheFactory.build(CacheType.RETROFIT_SERVICE_CACHE);
        }
        Preconditions.checkNotNull(mRetrofitServiceCache,
                "Cannot return null from a Cache.Factory#build(int) method");
        T retrofitService = (T) mRetrofitServiceCache.get(serviceClass.getCanonicalName());
        if (retrofitService == null) {
            retrofitService = mRetrofit.create(serviceClass);
            mRetrofitServiceCache.put(serviceClass.getCanonicalName(), retrofitService);
        }
        return retrofitService;
    }

    private <T> Method getRetrofitMethod(T service, Method method) throws NoSuchMethodException {
        return service.getClass().getMethod(method.getName(), method.getParameterTypes());
    }

    @Override
    public void clearAllCache() {

    }

    @NonNull
    @Override
    public Context getContext() {
        return null;
    }
}
