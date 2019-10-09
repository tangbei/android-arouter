package com.tang.module_home.main;

import android.app.Activity;

import com.tang.common.base.BaseResponse;
import com.tang.common.mvp.IModel;
import com.tang.common.mvp.IView;

import io.reactivex.Observable;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/12.
 */
public interface MainContract {

    interface View extends IView {
        Activity getActivity();
    }

    interface Model extends IModel {

        Observable<BaseResponse> getHome();
    }

}
