package com.xiefei.openmusicplayer.ui.local.SongLibrary.album;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.xiefei.mvpstructure.presenter.MvpBasePresenter;
import com.xiefei.openmusicplayer.entity.Album;
import com.xiefei.openmusicplayer.loader.AlbumLoader;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by xiefei on 16/7/10.
 */
public class AlbumListPresenter extends MvpBasePresenter<AlbumListView> implements LoaderManager.LoaderCallbacks<Cursor>{
    private Context context;
    private AlbumLoader albumLoader;
    private LoaderManager loaderManager;
    public AlbumListPresenter(Context context,LoaderManager loaderManager){
//        albumLoader = AlbumLoader.getInstance(context);
        this.context = context;
        this.loaderManager = loaderManager;
    }
//    void getData(){
//        rx.Observable.create(new rx.Observable.OnSubscribe<List<Album>>() {
//            @Override
//            public void call(Subscriber<? super List<Album>> subscriber) {
//                subscriber.onNext(albumLoader.getAlbums());
//            }
//        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<List<Album>>() {
//                    @Override
//                    public void call(List<Album> albums) {
//                        getView().setData(albums);
//                    }});
//    }
    void getData(){
        loaderManager.initLoader(0,null,this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new CursorLoader(context,MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI
                ,new String[]{"_id","album","minyear","artist","artist_id","numsongs"}
                ,null,null,MediaStore.Audio.Albums.DEFAULT_SORT_ORDER);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ArrayList arrayList = new ArrayList();
        Cursor cursor = data;
        if ((cursor != null) &&!cursor.isClosed()&& (cursor.moveToFirst()))
            do {
                Album album = getAlbumInfo(cursor);
                arrayList.add(album);
            }
            while (cursor.moveToNext());
//        if (cursor != null)
//            cursor.close();
        getView().setData(arrayList);
    }

    @NonNull
    protected Album getAlbumInfo(Cursor cursor) {
        return new Album(cursor.getLong(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getLong(4),cursor.getInt(5));
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    @Override
    public void cancel() {
        loaderManager.destroyLoader(0);
    }
}
