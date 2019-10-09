package com.tang.common.glide;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/17.
 */
public interface CacheStrategy {

    int ALL = 0;

    int NONE  = 1;

    int RESOURCE  = 2;

    int DATA  = 3;

    int AUTOMATIC  = 4;

    @IntDef({ALL,NONE,RESOURCE,DATA,AUTOMATIC})
    @Retention(RetentionPolicy.SOURCE)
    @interface Strategy{}
}
