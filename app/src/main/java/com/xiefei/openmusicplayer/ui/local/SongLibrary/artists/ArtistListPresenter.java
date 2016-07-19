package com.xiefei.openmusicplayer.ui.local.SongLibrary.artists;

import android.content.Context;

import com.xiefei.mvpstructure.presenter.MvpBasePresenter;
import com.xiefei.openmusicplayer.entity.Artist;
import com.xiefei.openmusicplayer.loader.ArtistLoader;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by xiefei on 16/7/10.
 */
public class ArtistListPresenter extends MvpBasePresenter<ArtistListView> {
    private Context context;
    private ArtistLoader artistLoader;
    public ArtistListPresenter(Context context){
        artistLoader = ArtistLoader.getInstance(context);
    }
    void getData(){
        rx.Observable.create(new rx.Observable.OnSubscribe<List<Artist>>() {
            @Override
            public void call(Subscriber<? super List<Artist>> subscriber) {
                subscriber.onNext(artistLoader.getArtists());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Artist>>() {
                    @Override
                    public void call(List<Artist> artists) {
                        getView().setData(artists);
                    }});
    }
}
