package com.xiefei.mvpstructure.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiefei.mvpstructure.presenter.MvpPresenter;
import com.xiefei.mvpstructure.view.MvpView;


/**
 * Created by xiefei on 2016/3/16.
 */
public abstract class MvpBaseFragment<P extends MvpPresenter,V extends MvpView> extends BaseFragment implements MvpView {
    protected P presenter;
    private boolean isAttatch = false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(presenter==null) 这个一个BUG 必须创建,因为一旦进入这个流程Context基本是会更新的.®

        setRetainInstance(isRetainInstance());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        presenter = createPresent();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //将P与V绑定
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.attachView(getMvpView());
        isAttatch = true;
        checkLazyLoad();
    }

    /**
     * 只有绑定成功后才能进行懒加载
     */
    @Override
    protected void checkLazyLoad() {
        if(isAttatch && getUserVisibleHint()){
            lazyLoad();
        }
    }

    public V getMvpView(){
        return (V) this;
    }
    @Override
    public void onDetach() {
        presenter.detachView(isRetainInstance());
        super.onDetach();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public abstract P createPresent();
    protected abstract boolean isRetainInstance();
}
