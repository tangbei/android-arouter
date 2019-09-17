package com.tang.app_common.http;

import com.tang.app_common.config.AppConfig;
import com.tang.app_common.lifecycle.Lifecycleable;
import com.tang.app_common.mvp.IView;
import com.tang.app_common.utils.RxLifecycleUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * 描述: retrofit请求中rxjava的订阅
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/16.
 */
public class HttpRequest {

    /**
     * 通用的请求订阅事件
     * @param mRootView 当前和rx 绑定的对象
     * @param observable
     * @param <T>
     * @return
     */
    public static <T> Observable observableView(IView mRootView, Observable<T> observable){
        return observable
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(AppConfig.maxRetries, AppConfig.retryDelaySecond))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe((Disposable disposable) -> {
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {}).compose(RxLifecycleUtils.bindToLifecycle(mRootView));//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
    }

    /**
     * 通用的请求订阅事件
     * @param lifecycleable 当前和rx 绑定的对象
     * @param observable
     * @param <T>
     * @return
     */
    public static <T> Observable observableLife(Lifecycleable lifecycleable, Observable<T> observable){
        return observable
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(AppConfig.maxRetries, AppConfig.retryDelaySecond))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe((Disposable disposable) -> {
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(lifecycleable));//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
    }

    /**
     * 通用的请求订阅事件-未绑定当前对象
     * @param observable
     * @param <T>
     * @return
     */
    public static <T> Observable observableUnable(Observable<T> observable){
        return observable
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(AppConfig.maxRetries, AppConfig.retryDelaySecond))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe((Disposable disposable) -> {
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                });//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
    }

}
