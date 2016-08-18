package com.xiefei.openmusicplayer.utils;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

/**
 * Created by xiefei on 16/7/10.
 */
public class OpenMusicPlayerUtils {
    public static Uri getAlbumArtUri(long paramInt) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), paramInt);
    }
    public static Uri getResourceDrawable(@NonNull Context context, @DrawableRes int drawableId){
        return Uri.parse("android:resource://"+context.getPackageName()+"/"+drawableId);
    }
}
