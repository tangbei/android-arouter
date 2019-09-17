package com.tang.app_common.router;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.tang.app_common.constant.Constant;
import com.tang.app_common.utils.LogUtil;

/**
 * 描述: 结果回调实现类
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/17.
 */
public class NavigationCallbackImpl implements NavigationCallback {

    @Override
    public void onFound(Postcard postcard) {
        LogUtil.d(Constant.Common.TAG,"---->onFound");
    }

    @Override
    public void onLost(Postcard postcard) {
        LogUtil.d(Constant.Common.TAG,"---->onLost");
    }

    @Override
    public void onArrival(Postcard postcard) {
        LogUtil.d(Constant.Common.TAG,"---->onArrival");
    }

    @Override
    public void onInterrupt(Postcard postcard) {
        LogUtil.d(Constant.Common.TAG,"---->onInterrupt");
    }
}
