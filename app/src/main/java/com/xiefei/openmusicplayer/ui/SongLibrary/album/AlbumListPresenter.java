package com.xiefei.openmusicplayer.ui.SongLibrary.album;

import android.content.Context;

import com.xiefei.mvpstructure.presenter.MvpBasePresenter;
import com.xiefei.openmusicplayer.entity.Album;
import com.xiefei.openmusicplayer.loader.AlbumLoader;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by xiefei on 16/7/10.
 */
public class AlbumListPresenter extends MvpBasePresenter<AlbumListView> {
    private Context context;
    private AlbumLoader albumLoader;
    public AlbumListPresenter(Context context){
        albumLoader = AlbumLoader.getInstance(context);
    }
    void getData(){
        rx.Observable.create(new rx.Observable.OnSubscribe<List<Album>>() {
            @Override
            public void call(Subscriber<? super List<Album>> subscriber) {
                subscriber.onNext(albumLoader.getAlbums());
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Action1<List<Album>>() {
                    @Override
                    public void call(List<Album> albums) {
                        getView().setData(albums);
                    }});
    }
}
