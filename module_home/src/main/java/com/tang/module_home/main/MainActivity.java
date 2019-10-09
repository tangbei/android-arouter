package com.tang.module_home.main;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.tang.app_common.base.BaseActivity;
import com.tang.app_common.constant.Constant;
import com.tang.app_common.livedata.LiveDataBus;
import com.tang.app_common.mvp.IModel;
import com.tang.app_common.router.ARouterManager;
import com.tang.app_common.router.CommonRouter;
import com.tang.app_common.utils.LogUtil;
import com.tang.app_common.utils.ToastUtil;
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

        String tsst = getIntent().getStringExtra("test");
        LogUtil.d(Constant.Common.TAG,"----->"+tsst);
        mPresenter.setLogin();

        LiveDataBus.get().with("key_ceshi",String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        ToastUtil.show(MainActivity.this,s);
                    }
                });

        LiveDataBus.get().with("key_back",String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        ToastUtil.show(MainActivity.this,s);
                    }
                });
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    public void btn_liveData(View view){
        LiveDataBus.get().with("key_test").setValue("我是上个页面的发布者测试哦");
        ARouterManager.startActivity(this,CommonRouter.PATH_APP_TESTS_ACTIVITY,null);
    }
}
