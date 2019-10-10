package com.tang.common.glide;

import android.content.Context;
import android.support.annotation.Nullable;

import com.tang.frame.other.Preconditions;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/17.
 */
public class ImageLoader {

    private GlideImageLoaderStrategy mStrategy;

    private volatile static ImageLoader imageLoader;

    public ImageLoader() {
        mStrategy = new GlideImageLoaderStrategy();
    }

    public static ImageLoader getInstance(){
        if (null == imageLoader){
            synchronized (ImageLoader.class){
                if (null == imageLoader){
                    imageLoader = new ImageLoader();
                }
            }
        }
        return imageLoader;
    }

    /**
     * 加载图片
     *
     * @param context
     * @param config
     * @param <T>
     */
    public <T extends ImageConfig> void loadImage(Context context, T config) {
        Preconditions.checkNotNull(mStrategy, "Please implement BaseImageLoaderStrategy and call GlobalConfigModule.Builder#imageLoaderStrategy(BaseImageLoaderStrategy) in the applyOptions method of ConfigModule");
        this.mStrategy.loadImage(context, (ImageConfigImpl) config);
    }

    /**
     * 停止加载或清理缓存
     *
     * @param context
     * @param config
     * @param <T>
     */
    public <T extends ImageConfig> void clear(Context context, T config) {
        Preconditions.checkNotNull(mStrategy, "Please implement BaseImageLoaderStrategy and call GlobalConfigModule.Builder#imageLoaderStrategy(BaseImageLoaderStrategy) in the applyOptions method of ConfigModule");
        this.mStrategy.clear(context, (ImageConfigImpl) config);
    }

    /**
     * 可在运行时随意切换 {@link BaseImageLoaderStrategy}
     *
     * @param strategy
     */
    public void setLoadImgStrategy(BaseImageLoaderStrategy strategy) {
        Preconditions.checkNotNull(strategy, "strategy == null");
        this.mStrategy = (GlideImageLoaderStrategy) strategy;
    }

    @Nullable
    public BaseImageLoaderStrategy getLoadImgStrategy() {
        return mStrategy;
    }
}
