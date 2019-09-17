package com.tang.module_home.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.tang.app_common.base.BaseActivity;
import com.tang.app_common.mvp.IModel;
import com.tang.app_common.router.CommonRouter;
import com.tang.module_home.R;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/11.
 */
@Route(path = CommonRouter.PATH_APP_MAIN_ACTIVITY)
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public IModel getModel() {
        return new MainModel();
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.setLogin();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}
