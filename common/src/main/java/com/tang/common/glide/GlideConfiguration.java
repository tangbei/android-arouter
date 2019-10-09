package com.tang.common.glide;

import android.content.Context;
import android.support.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.tang.common.manager.RequestManager;
import java.io.InputStream;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/17.
 */
@GlideModule(glideName = "appGlide")
public class GlideConfiguration extends AppGlideModule {

    public static final int IMAGE_DISK_CACHE_MAX_SIZE = 100 * 1024 * 1024;//图片缓存文件最大值为100Mb

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        RequestManager requestManager = new RequestManager();
        builder.setDiskCache(new DiskCache.Factory() {
            @Override
            public DiskCache build() {
                // Careful: the external cache directory doesn't enforce permissions
                return DiskLruCacheWrapper.create(requestManager.getCacheFile(), IMAGE_DISK_CACHE_MAX_SIZE);
            }
        });

        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

        //将配置 Glide 的机会转交给 GlideImageLoaderStrategy,如你觉得框架提供的 GlideImageLoaderStrategy
        //并不能满足自己的需求,想自定义 BaseImageLoaderStrategy,那请你最好实现 GlideAppliesOptions
        //因为只有成为 GlideAppliesOptions 的实现类,这里才能调用 applyGlideOptions(),让你具有配置 Glide 的权利
        BaseImageLoaderStrategy loadImgStrategy = new GlideImageLoaderStrategy();
        if (loadImgStrategy != null && loadImgStrategy instanceof GlideAppliesOptions) {
            ((GlideAppliesOptions) loadImgStrategy).applyGlideOptions(context, builder);
        }
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
        //Glide 默认使用 HttpURLConnection 做网络请求,在这切换成 Okhttp 请求
        RequestManager requestManager = new RequestManager();
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(requestManager.provideClient()));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
