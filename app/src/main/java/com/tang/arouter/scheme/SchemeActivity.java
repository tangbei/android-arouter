package com.tang.arouter.scheme;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tang.app_common.base.BaseActivity;
import com.tang.arouter.R;

/**
 * 描述: 外部进入
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/17.
 */
public class SchemeActivity extends BaseActivity {

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_scheme;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        Uri uri = getIntent().getData();
        if (null != uri){
            ARouter.getInstance().build(uri).navigation();
            finish();
        }
    }
}
