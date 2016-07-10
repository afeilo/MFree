package com.xiefei.openmusicplayer.utils;

import android.content.ContentUris;
import android.net.Uri;

/**
 * Created by xiefei on 16/7/10.
 */
public class OpenMusicPlayerUtils {
    public static Uri getAlbumArtUri(long paramInt) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), paramInt);
    }
}
