package com.xiefei.openmusicplayer.ui.local.SongLibrary.filterSong;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import com.xiefei.mvpstructure.presenter.MvpBasePresenter;
import com.xiefei.openmusicplayer.entity.SongInfo;

import java.util.ArrayList;

/**
 * Created by xiefei on 16/7/10.
 */
public class FilterSongListPresenter extends MvpBasePresenter<FilterSongListView>  implements LoaderManager.LoaderCallbacks<Cursor>{
    private Context context;
    private LoaderManager loaderManager;
    private CursorLoader cursorLoader;
    public FilterSongListPresenter(Context context,LoaderManager loaderManager){
        this.context = context;
        this.loaderManager = loaderManager;
    }
    void getData(String selection){
        getView().showLoading(true);
        Bundle bundle = new Bundle();
        bundle.putString("selection",selection);
        if(loaderManager.getLoader(100) != null){
            loaderManager.restartLoader(100,bundle,this);
        }else {
            loaderManager.initLoader(100,bundle,this);
        }
    }

    @Override
    public void cancel() {

        if(cursorLoader !=null)
            cursorLoader.cancelLoadInBackground();
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        String selection = args.getString("selection");
        cursorLoader = new CursorLoader(context, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,    new String[]{MediaStore.Audio.Media._ID,MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION,MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM_ID},selection,null,MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ArrayList arrayList = new ArrayList();
        if ((data != null) && (data.moveToFirst()))
            do {
                SongInfo songInfo = getSongInfo(data);
                arrayList.add(songInfo);
            }
            while (data.moveToNext());
        if(getView()!=null)
            getView().setData(arrayList);
    }


    @Override
    public void onLoaderReset(Loader loader) {
        Log.d(getClass().getName().toString(),"loaderReset");
    }

    @NonNull
    private SongInfo getSongInfo(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
        String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
        String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
        long albumId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
        int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
        String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
        return new SongInfo(id, title , album, albumId, artist, duration, url);
    }
}
