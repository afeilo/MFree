package com.xiefei.openmusicplayer.loader;

import android.content.Context;

import com.xiefei.openmusicplayer.API.BaiduService;
import com.xiefei.openmusicplayer.entity.SongMenu;
import com.xiefei.openmusicplayer.entity.SongMenuInfo;
import com.xiefei.openmusicplayer.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by xiefei on 16/7/10.
 */
public class SongMenuLoader {
    private static SongMenuLoader songMenuLoader = null;
    private BaiduService baiduService;
    private SongMenuLoader(){
         baiduService = new Retrofit
                .Builder()
                .baseUrl(Constant.ONLINE_BASE_URL)
                 .addConverterFactory(GsonConverterFactory.create())
                 .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build().create(BaiduService.class);
    }
    public static SongMenuLoader getInstance(){
        if(songMenuLoader == null){
            synchronized (SongLoader.class){
                if(songMenuLoader == null)
                    songMenuLoader = new SongMenuLoader();
            }
        }
        return songMenuLoader;
    }
    public Call<SongMenu> getSongMenus(int pageSize, int pageNo){
        return baiduService.getSongMenus(pageSize,pageNo);
    }
    public Observable<SongMenuInfo> getSongMenuInfos(String listId){
        return baiduService.getSongMenuInfo(listId);
    }
}
