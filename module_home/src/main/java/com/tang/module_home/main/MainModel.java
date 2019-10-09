package com.tang.module_home.main;

import com.tang.common.base.BaseResponse;
import com.tang.common.http.HttpService;
import com.tang.common.manager.IRepositoryManager;
import com.tang.common.manager.RepositoryManager;
import com.tang.common.mvp.BaseModel;
import com.tang.common.utils.LogUtil;
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
