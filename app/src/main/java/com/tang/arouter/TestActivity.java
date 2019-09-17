package com.tang.arouter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tang.app_common.base.BaseActivity;
import com.tang.app_common.router.CommonRouter;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/12.
 */
@Route(path = CommonRouter.PATH_APP_TEST_ACTIVITY)
public class TestActivity extends BaseActivity {

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_test;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

}
