package com.example.xiefei.openmusicplayer;

import android.test.AndroidTestCase;
import android.util.Log;

import com.xiefei.openmusicplayer.loader.AlbumLoader;
import com.xiefei.openmusicplayer.loader.ArtistLoader;
import com.xiefei.openmusicplayer.loader.SongLoader;
import com.xiefei.openmusicplayer.service.helper.handler.BaiduRequestHandler;

import rx.Observable;
import rx.Observer;

/**
 * Created by xiefei on 16/7/9.
 */
public class RequestHandlerTest extends AndroidTestCase{
    private static String Tag  = "RequestHandlerTest";
    public void testGetSongs(){
        BaiduRequestHandler baiduRequestHandler = new BaiduRequestHandler();
        Observable observable = baiduRequestHandler.handleRequest("7316464",getContext());
        observable.subscribe(new Observer() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d("onError",e.getMessage());
            }

            @Override
            public void onNext(Object o) {
                Log.d("onNext",o.toString());
            }
        });
    }
}
