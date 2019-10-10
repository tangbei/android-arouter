package com.tang.frame.mvp;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/11.
 */
public interface IPresenter {

    void getRootView();

    /**
     * 做一些初始化操作
     */
    void onStart();

    /**
     * 销毁操作
     */
    void onDestroy();
}
