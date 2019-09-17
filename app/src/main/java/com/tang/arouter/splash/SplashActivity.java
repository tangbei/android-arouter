package com.tang.arouter.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.tang.app_common.base.BaseActivity;
import com.tang.app_common.constant.Constant;
import com.tang.app_common.router.ARouterManager;
import com.tang.app_common.router.CommonRouter;
import com.tang.arouter.R;

import butterknife.OnClick;

@Route(path = CommonRouter.PATH_APP_SPLASH_ACTIVITY)
public class SplashActivity extends BaseActivity {

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_splash;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        Thread thread = new Thread(() -> {
        });

        thread.start();
    }

    @OnClick(R.id.btn_click)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        bundle.putString("test","我是上层传过来的参数");
        ARouterManager.startActivity(this,CommonRouter.PATH_APP_MAIN_ACTIVITY,bundle);
    }
}
