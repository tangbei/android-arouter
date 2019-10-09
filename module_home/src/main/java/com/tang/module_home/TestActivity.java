package com.tang.module_home;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tang.app_common.base.BaseActivity;
import com.tang.app_common.livedata.LiveDataBus;
import com.tang.app_common.router.CommonRouter;
import com.tang.app_common.utils.AppUtil;
import com.tang.app_common.utils.LogUtil;
import com.tang.app_common.utils.ToastUtil;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/23.
 */
@Route(path = CommonRouter.PATH_APP_TESTS_ACTIVITY)
public class TestActivity extends BaseActivity {
    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_test;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        LiveDataBus.get().with("key_test",String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        ToastUtil.show(TestActivity.this,s);
                    }
                });

        LogUtil.d("tang-->", AppUtil.getDetailId(this));
    }

    public void btnSend(View view){
        LiveDataBus.get().with("key_back").setValue("我是上个页面的发布者测试哦");
    }
}
