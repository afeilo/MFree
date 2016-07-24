package com.xiefei.mvpstructure.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
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
    private static final String Tag = BaseFragment.class.getName();
    private ViewGroup container;
    protected Boolean isVisible = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 避免多次从xml中加载布局文件
        if (mContentView == null) {
            initView(savedInstanceState);
            lazyLoad();
            setListener();
            processLogic(savedInstanceState);
        } else {
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            if (parent != null) {
                parent.removeView(mContentView);
            }
        }
        return mContentView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){
            if(getUserVisibleHint()){
                isVisible = true;
                onVisible();
            }else {
                if(isVisible){
                    onUnvisible();
                }
                isVisible =false;
            }
        }
    }
    protected void onVisible(){
        lazyLoad();
    }
    protected void onUnvisible(){}
    protected abstract void lazyLoad();
    /**
     * 初始化View控件
     */
    protected abstract void initView(Bundle savedInstanceState);
    /*
    为View设置监听器
     */
    protected abstract void setListener();
    /**
     * 处理业务逻辑，状态恢复等操作
     *
     * @param savedInstanceState
     */
    protected abstract void processLogic(Bundle savedInstanceState);
    /**
     * 查找View
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return
     */
    protected <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) mContentView.findViewById(id);
    }
    /**
     * 当fragment对用户可见时，会调用该方法，可在该方法中懒加载网络数据
     */
    protected abstract void onUserVisible();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup)mContentView.getParent()).removeView(mContentView);
        Log.d(Tag,"mContentView.getParent():"+mContentView.getParent());
    }
}
