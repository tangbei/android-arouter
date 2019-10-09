package com.tang.common.lifecycle;

import android.support.annotation.NonNull;

import io.reactivex.subjects.Subject;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/16.
 */
public interface Lifecycleable<E> {
    @NonNull
    Subject<E> provideLifecycleSubject();
}

