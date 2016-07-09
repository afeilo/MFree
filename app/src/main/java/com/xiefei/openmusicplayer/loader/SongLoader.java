package com.xiefei.openmusicplayer.loader;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.xiefei.openmusicplayer.entity.SongInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiefei on 16/6/29.
 * 获取歌曲 从ContentProvider中获取,所以一旦有新增加的歌曲操作需要我们进行更新.
 */
public class SongLoader {
    private ArrayList arrayList = null;
    private Context context = null;
    private static SongLoader songLoader = null;
    private SongLoader(Context context){
        this.context = context;
    }
    public static SongLoader getInstance(Context context){
        if(songLoader == null){
            synchronized (SongLoader.class){
                if(songLoader == null)
                    songLoader = new SongLoader(context.getApplicationContext());
            }
        }
        return songLoader;
    }
    public List<SongInfo> getSongs(){
        return getSongs(null,null,false);
    }
    public List<SongInfo> getSongs(String selection,String[] selectionArgs){
        return getSongs(selection,selectionArgs,false);
    }

    /**
     *
     * @param selection
     * @param needToFresh 是否刷新列表
     * @return
     */
    public List<SongInfo> getSongs(String selection, String[] selectionArgs,boolean needToFresh){
        if(!needToFresh && arrayList != null){
            return arrayList;
        }
        arrayList = new ArrayList();
        Cursor cursor = getCursor(selection,selectionArgs);
        if ((cursor != null) && (cursor.moveToFirst()))
            do {
                SongInfo songInfo = getSongInfo(cursor);
                arrayList.add(songInfo);
            }
            while (cursor.moveToNext());
        if (cursor != null)
            cursor.close();
        return arrayList;

    }

    @NonNull
    protected SongInfo getSongInfo(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
        String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
        String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
        int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
        String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
        return new SongInfo(id, title , album, artist, duration, url);
    }

    private Cursor getCursor(String selection,String[] selectionArgs){
        String sectionParam = "is_music=1";
        if(selection!=null)
            sectionParam+="AND"+selection;
        Cursor cursor = context.getContentResolver().query
                (MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                        ,new String[]{MediaStore.Audio.Media._ID,MediaStore.Audio.Media.TITLE,
                                MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.ALBUM,
                                MediaStore.Audio.Media.DURATION,MediaStore.Audio.Media.DATA},
                        sectionParam,selectionArgs,MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        return cursor;
    }
}
