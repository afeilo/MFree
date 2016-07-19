package com.xiefei.mvpstructure.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xiefei.mvpstructure.presenter.MvpBasePresenter;
import com.xiefei.mvpstructure.presenter.MvpPresenter;
import com.xiefei.mvpstructure.view.MvpView;

/**
 * Created by xiefei on 16/7/13.
 */
public abstract class MvpBaseActivity<P extends MvpPresenter,V extends MvpView> extends AppCompatActivity implements MvpView{
    protected P presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(presenter == null)
            presenter = createPresent();
        presenter.attachView(getMvpView());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView(false);
    }

    protected V getMvpView(){
        return (V) this;
    }


    protected abstract P createPresent();

}
