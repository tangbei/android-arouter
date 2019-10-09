package com.tang.common.http;

import com.tang.common.base.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/16.
 */
public interface HttpService {

    /**
     * 首页今日推荐和轮播位接口
     * @return
     */
    @GET("/merchant/back/product/recommends")
    Observable<BaseResponse> getHomeRecommend();
}
