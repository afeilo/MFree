package com.xiefei.mvpstructure.presenter;




import com.xiefei.mvpstructure.view.MvpView;

import java.lang.ref.WeakReference;

/**
 * Created by xiefei on 2016/3/16.
 */
public abstract class MvpBasePresenter<V extends MvpView> implements MvpPresenter<V>{
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

    /**
     * 如果为可持有则不需要中断异步任务
     * @param retainInstance 是否为可持有
     */
    @Override
    public void detachView(boolean retainInstance) {
        if(weakReference!=null){
            weakReference.clear();
            weakReference = null;
        }
        if(retainInstance == false){
            cancel();
        }
    }
    public abstract void cancel();
}
