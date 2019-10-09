package com.tang.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.View;

import com.tang.common.delegate.IActivity;
import com.tang.common.lifecycle.ActivityLifecycleable;
import com.tang.common.mvp.IPresenter;
import com.trello.rxlifecycle2.android.ActivityEvent;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/11.
 */
public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements IActivity, ActivityLifecycleable {

    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<ActivityEvent> mLifecycleSubject = BehaviorSubject.create();
//    private Cache<String, Object> mCache;
    private Unbinder mUnbinder;
    @Nullable
    protected P mPresenter;//如果当前页面逻辑简单, Presenter 可以为 null

    @NonNull
    @Override
    public Subject<ActivityEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
//        View view = convertAutoView(name, context, attrs);
        View view = null;
        return view == null ? super.onCreateView(name, context, attrs) : view;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            int layoutResID = initView(savedInstanceState);
            //如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
            if (layoutResID != 0) {
                setContentView(layoutResID);
                //绑定到butterknife
                mUnbinder = ButterKnife.bind(this);
            }
        } catch (Exception e) {
            if (e instanceof InflateException) throw e;
            e.printStackTrace();
        }
        if (null != createPresenter()){
            mPresenter = createPresenter();
            mPresenter.getRootView();
        }
        initData(savedInstanceState);
    }

    /**
     * 如果页面简单，可以不创建presenter
     * @return
     */
    protected P createPresenter() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY)
            mUnbinder.unbind();
        this.mUnbinder = null;
        if (mPresenter != null)
            mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
    }
}
