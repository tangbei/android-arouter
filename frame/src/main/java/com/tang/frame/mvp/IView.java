package com.tang.frame.mvp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/12.
 */
public interface IView {

    /**
     * 显示加载
     */
    default void showLoading() {}

    /**
     * 隐藏加载
     */
    default void hideLoading() {

    }

    default IModel getModel(){
        return null;
    }

    /**
     * 显示信息
     *
     * @param message 消息内容, 不能为 {@code null}
     */
    void showMessage(@NonNull String message);

    /**
     * 跳转 {@link Activity}
     *
     * @param intent {@code intent} 不能为 {@code null}
     */
    default void launchActivity(@NonNull Intent intent) {
//        checkNotNull(intent);
//        ArmsUtils.startActivity(intent);
    }

    /**
     * 杀死自己
     */
    default void killMyself() {

    }

}
