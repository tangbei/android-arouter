package com.tang.module_home.main;

import com.tang.common.base.BaseResponse;
import com.tang.common.constant.Constant;
import com.tang.common.http.HttpRequest;
import com.tang.common.mvp.BasePresenter;
import com.tang.common.utils.LogUtil;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/12.
 */
public class MainPresenter extends BasePresenter<MainContract.Model,MainContract.View> {

    public MainPresenter(MainContract.View rootView) {
        super(rootView);
    }

    public void setLogin(){
        mRootView.getActivity();
//        mModel.getHome();
        LogUtil.d("tang--------测试--->","aaaaaaaaaaaaaaa");

        HttpRequest.observableView(mRootView,mModel.getHome())
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse o) {
                        LogUtil.d("wp来了哦");
                    }
                });
//        HttpManager.getCommonApi().getHomeRecommend();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d(Constant.Common.TAG,"我是mainPresenter的销毁操作");
    }
}
