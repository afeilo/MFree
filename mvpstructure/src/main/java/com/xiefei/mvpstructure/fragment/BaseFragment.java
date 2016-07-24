package com.xiefei.mvpstructure.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xiefei on 16/7/24.
 */
public abstract class BaseFragment extends Fragment{
    private View mContentView;//默认加载的布局
    private final String Tag = this.getClass().getName();
    protected Boolean isVisible = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 避免多次从xml中加载布局文件
        if (mContentView == null) {
            setContentView(getLayoutRes(),container);
            initView(mContentView);
        } else {
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            if (parent != null) {
                parent.removeView(mContentView);
            }
        }
        return mContentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(Tag,"userVisible->"+getUserVisibleHint());
        if(isVisibleToUser) {
            isVisible = true;
            onVisible();
            checkLazyLoad();
        }else{
            if(isVisible){
                unVisible();
            }
            isVisible =false;
        }

    }

    protected void checkLazyLoad() {
        if(mContentView!=null&&getUserVisibleHint()){
            lazyLoad();
        }
    }
    protected  void lazyLoad(){}
    /**
     * 初始化View(第一次初始化时候才会调用,注意一般该方法只用户绑定View)
     * @param contentView
     */
    protected abstract void initView(View contentView);

    /**
     * 当界面可见时调用
     */
    protected void onVisible(){}

    /**
     * 当界面不可见时调用(主要用于实现懒加载)
     */
    protected void unVisible(){}
    protected abstract @LayoutRes int getLayoutRes();
    protected void setContentView(@LayoutRes int layoutResID, ViewGroup container) {
        mContentView = LayoutInflater.from(getContext()).inflate(layoutResID, container,false);
    }
    protected <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) mContentView.findViewById(id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup)mContentView.getParent()).removeView(mContentView);
        Log.d(Tag,"mContentView.getParent():"+mContentView.getParent());
    }
}
