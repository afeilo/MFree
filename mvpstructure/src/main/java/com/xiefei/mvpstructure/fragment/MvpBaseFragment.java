package com.xiefei.mvpstructure.fragment;

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
public abstract class MvpBaseFragment<P extends MvpPresenter,V extends MvpView> extends Fragment implements MvpView {
    protected P presenter;
    protected View backView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(presenter==null)
            presenter = createPresent();
        setRetainInstance(isRetainInstance());
    }

    //将P与V绑定
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.attachView(getMvpView());
        bindData(view);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        if(backView == null)
            backView = inflater.inflate(getLayout(),container,false);
        return backView;
    }
    public V getMvpView(){
        return (V) this;
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView(isRetainInstance());
        setRetainInstance(isRetainInstance());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public abstract @LayoutRes int getLayout();
    public abstract P createPresent();
    protected abstract boolean isRetainInstance();
    protected abstract void bindData(View v);
}
