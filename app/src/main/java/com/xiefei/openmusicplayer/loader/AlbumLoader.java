package com.xiefei.openmusicplayer.loader;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;

import com.xiefei.openmusicplayer.entity.Album;
import com.xiefei.openmusicplayer.entity.Album;
import com.xiefei.openmusicplayer.entity.SongInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiefei on 16/6/29.
 * 获取所有专辑 从ContentProvider中获取,所以一旦有新增加的歌曲操作需要我们进行更新.
 */
public class AlbumLoader{
    private static AlbumLoader albumLoader = null;
    private Context context = null;
    ArrayList arrayList = null;
    private AlbumLoader(Context context){
        this.context = context;
    }
    public static AlbumLoader getInstance(Context context){
        if(albumLoader==null){
            synchronized (AlbumLoader.class){
                if(albumLoader == null)
                    albumLoader = new AlbumLoader(context.getApplicationContext());
            }
        }
        return albumLoader;
    }
    public List<Album> getAlbums(){
        return getAlbums(null,null,false);
    }
    public List<Album> getAlbums(String selection, String[] selectionArgs){
        return getAlbums(selection,selectionArgs,false);
    }
    /**
     *
     * @param selection
     * @param needToFresh 是否刷新列表
     * @return
     */
    public synchronized List<Album> getAlbums(String selection, String[] selectionArgs,boolean needToFresh){
        if(!needToFresh && arrayList != null){
            return arrayList;
        }
        arrayList = new ArrayList();
        Cursor cursor = getCursor(selection,selectionArgs);
        if ((cursor != null) && (cursor.moveToFirst()))
            do {
                Album album = getAlbumInfo(cursor);
                arrayList.add(album);
            }
            while (cursor.moveToNext());
        if (cursor != null)
            cursor.close();
        return arrayList;

    }

    @NonNull
    protected Album getAlbumInfo(Cursor cursor) {
        return new Album(cursor.getLong(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getLong(4),cursor.getInt(5));
    }

    protected Cursor getCursor(String selection, String[] selectionArgs){
        Cursor cursor = context.getContentResolver().query
                (MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI
                        ,new String[]{"_id","album","minyear","artist","artist_id","numsongs"}
                        ,selection,selectionArgs,MediaStore.Audio.Albums.DEFAULT_SORT_ORDER);
        return cursor;
    }
}
