package com.xiefei.mvpstructure.presenter;


import com.xiefei.mvpstructure.view.MvpView;

/**
 * Created by xiefei on 2016/3/16.
 */
public interface MvpPresenter<V extends MvpView> {
    /*
    将MvpView与该Presenter进行绑定
     */
    public void attachView(V view);
    /*
    当destroyed时调用，
     */
    public void detachView(boolean retainInstance);
}
