package com.tang.module_home.main;

import com.tang.app_common.base.BaseResponse;
import com.tang.app_common.http.HttpService;
import com.tang.app_common.manager.IRepositoryManager;
import com.tang.app_common.manager.RepositoryManager;
import com.tang.app_common.mvp.BaseModel;
import com.tang.app_common.utils.LogUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * 描述: model数据
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/16.
 */
public class MainModel extends BaseModel implements MainContract.Model {

    public MainModel() {
        this(RepositoryManager.getInstance());
    }

    public MainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResponse> getHome() {
        LogUtil.d("tang----------->","我是model的内容");
        return Observable.just(
                mRepositoryManager.obtainRetrofitService(HttpService.class)
                .getHomeRecommend()
        ).flatMap(new Function<Observable<BaseResponse>, ObservableSource<BaseResponse>>() {
            @Override
            public ObservableSource<BaseResponse> apply(Observable<BaseResponse> baseResponseObservable) throws Exception {
                return baseResponseObservable;
            }
        });
    }

    @Override
    public void onDestroy() {
        LogUtil.d("tang--->","我------>是model的销毁操作");
    }
}
