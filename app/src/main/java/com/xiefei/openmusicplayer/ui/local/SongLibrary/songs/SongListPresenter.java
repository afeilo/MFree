package com.xiefei.openmusicplayer.ui.local.SongLibrary.songs;

import android.content.Context;

import com.xiefei.mvpstructure.presenter.MvpBasePresenter;
import com.xiefei.openmusicplayer.entity.SongInfo;
import com.xiefei.openmusicplayer.loader.SongLoader;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by xiefei on 16/7/10.
 */
public class SongListPresenter extends MvpBasePresenter<SongListView> {
    private Context context;
    private SongLoader songLoder;
    public SongListPresenter(Context context){
        songLoder = SongLoader.getInstance(context);
    }
    void getData(){
        rx.Observable.create(new rx.Observable.OnSubscribe<List<SongInfo>>() {
            @Override
            public void call(Subscriber<? super List<SongInfo>> subscriber) {
                subscriber.onNext(songLoder.getSongs());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<SongInfo>>() {
                    @Override
                    public void call(List<SongInfo> songInfos) {
                        getView().setData(songInfos);
                    }});
    }
}
