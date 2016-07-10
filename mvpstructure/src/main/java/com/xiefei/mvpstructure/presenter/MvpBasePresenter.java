package com.xiefei.mvpstructure.presenter;




import com.xiefei.mvpstructure.view.MvpView;

import java.lang.ref.WeakReference;

/**
 * Created by xiefei on 2016/3/16.
 */
public class MvpBasePresenter<V extends MvpView> implements MvpPresenter<V>{
    private WeakReference<V> weakReference;
    @Override
    public void attachView(V view) {
        weakReference = new WeakReference<V>(view);
    }
    public V getView(){
        return weakReference==null?null:weakReference.get();
    }
    public boolean isDetached(){
        return weakReference==null?false:true;
    }
    @Override
    public void detachView(boolean retainInstance) {
        if(weakReference!=null){
            weakReference.clear();
            weakReference = null;
        }
    }
}
