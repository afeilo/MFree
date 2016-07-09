package com.example.xiefei.openmusicplayer;

import android.test.AndroidTestCase;
import android.util.Log;

import com.xiefei.openmusicplayer.loader.AlbumLoader;
import com.xiefei.openmusicplayer.loader.ArtistLoader;
import com.xiefei.openmusicplayer.loader.SongLoader;

/**
 * Created by xiefei on 16/7/9.
 */
public class LoaderTest extends AndroidTestCase{
    private static String Tag  = LoaderTest.class.getName();
    public void testGetSongs(){
        Log.d(Tag,SongLoader.getInstance(getContext()).getSongs().toString());
    }
    public void testGetAlbums(){
        Log.d(Tag, AlbumLoader.getInstance(getContext()).getAlbums().toString());
//        System.out.print(AlbumLoader.getInstance(getContext()).getAlbums().toString());
    }
    public void testGetArtists(){
        Log.d(Tag, ArtistLoader.getInstance(getContext()).getArtists().toString());
    }
}
