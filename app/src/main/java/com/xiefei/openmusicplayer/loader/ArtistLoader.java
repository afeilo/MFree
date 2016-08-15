package com.xiefei.openmusicplayer.loader;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import com.xiefei.openmusicplayer.entity.Artist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiefei on 16/6/29.
 * 获取所有专辑 从ContentProvider中获取,所以一旦有新增加的歌曲操作需要我们进行更新.
 */
public class ArtistLoader {
    private static ArtistLoader albumLoader = null;
    private Context context = null;
    ArrayList arrayList = null;
    private ArtistLoader(Context context){
        this.context = context;
    }
    public static ArtistLoader getInstance(Context context){
        if(albumLoader==null){
            synchronized (ArtistLoader.class){
                if(albumLoader == null)
                    albumLoader = new ArtistLoader(context.getApplicationContext());
            }
        }
        return albumLoader;
    }
    public List<Artist> getArtists(){
        return getArtists(null,null,false);
    }
    public List<Artist> getArtists(String selection, String[] selectionArgs){
        return getArtists(selection,selectionArgs,false);
    }
    /**
     *
     * @param selection
     * @param needToFresh 是否刷新列表
     * @return
     */
    public synchronized List<Artist> getArtists(String selection, String[] selectionArgs,boolean needToFresh){
        if(!needToFresh && arrayList != null){
            return arrayList;
        }
        arrayList = new ArrayList();
        Cursor cursor = getCursor(selection,selectionArgs);
        if ((cursor != null) && (cursor.moveToFirst()))
            do {
                Artist album = getArtistInfo(cursor);
                arrayList.add(album);
            }
            while (cursor.moveToNext());
        if (cursor != null)
            cursor.close();
        return arrayList;

    }

    @NonNull
    protected Artist getArtistInfo(Cursor cursor) {
        return new Artist(cursor.getLong(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3));
    }

    protected Cursor getCursor(String selection, String[] selectionArgs){
        return context.getContentResolver().query
                (MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI
                        ,new String[]{"_id","artist","number_of_albums","number_of_tracks"}
                        ,selection,selectionArgs,MediaStore.Audio.Artists.DEFAULT_SORT_ORDER);
    }
}
