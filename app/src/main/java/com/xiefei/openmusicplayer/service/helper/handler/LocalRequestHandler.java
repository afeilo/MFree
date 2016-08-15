package com.xiefei.openmusicplayer.service.helper.handler;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.xiefei.openmusicplayer.service.ServiceMusicEntity;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by xiefei on 16/8/14.
 */
public class LocalRequestHandler extends RequestHandler{
    String mediaProject[] = new String[]{MediaStore.Audio.Media._ID,MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DURATION,MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM_ID};
    @Override
    public Observable<ServiceMusicEntity> handleRequest(final String request, final Context context) {
        return Observable.create(new Observable.OnSubscribe<ServiceMusicEntity>() {
            @Override
            public void call(Subscriber<? super ServiceMusicEntity> subscriber) {
                Cursor cursor = context.getContentResolver().query(getUriById(request),mediaProject,null,null,null);
                if(cursor!=null&&cursor.moveToFirst()){
                    ServiceMusicEntity entity = new ServiceMusicEntity();
                    entity.setId(cursor.getLong(0));
                    entity.setTitle(cursor.getString(1));
                    entity.setArtist(cursor.getString(2));
                    entity.setAlbum(cursor.getString(3));
                    entity.setUrl(cursor.getString(5));
                    entity.setDescPic(getAlbumArtUri(entity.getId()).getPath());
                    cursor.close();
                    subscriber.onNext(entity);
                }else {
                    subscriber.onError(new Exception("文件未找到"));
                }
            }
        });
    }

    private Uri getUriById(String id){
        return Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, String.valueOf(id));
    }

    private Uri getAlbumArtUri(long paramInt) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), paramInt);
    }

}
