package com.xiefei.openmusicplayer.service.helper;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;

import com.xiefei.openmusicplayer.service.MusicService;
import com.xiefei.openmusicplayer.service.ServiceMusicEntity;
import com.xiefei.openmusicplayer.service.helper.handler.BaiduRequestHandler;
import com.xiefei.openmusicplayer.service.helper.handler.LocalRequestHandler;

import java.lang.ref.WeakReference;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xiefei on 16/8/14.
 * 用来帮助MusicService获取音乐文件基本信息.
 */
public class MusicHelper {
    //歌曲来源
    public static final int local = 0;//本地音乐
//    public static final int file = 0;//本地音乐
    public static final int baidu_online = 1;//百度音乐接口
    private WeakReference<Context> reference;
    private final LocalRequestHandler localRequestHandler = new LocalRequestHandler();
    private final BaiduRequestHandler baiduRequestHandler = new BaiduRequestHandler();
    public MusicHelper(Context context){
        if(reference == null)
            reference = new WeakReference<>(context);
    }
    private Observable<ServiceMusicEntity> musicEntityObservable;
    public void getMessageById(String id, int onlineTag, final LoadMusicCallBack callBack){
//        if(musicEntityObservable!=null);
        musicEntityObservable  = getEntityObservable(id, onlineTag);
        musicEntityObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<ServiceMusicEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(callBack!=null)
                            callBack.loadFail(e);
                    }

                    @Override
                    public void onNext(ServiceMusicEntity entity) {
                        if(callBack!=null)
                            callBack.loadSuccess(entity);
                    }
        });
    }

    private Observable<ServiceMusicEntity> getEntityObservable(String id, int onlineTag) {
        Observable<ServiceMusicEntity> musicEntityObservable = null;
        switch (onlineTag){
            case local:
                musicEntityObservable = localRequestHandler.handleRequest(id,reference.get().getApplicationContext());
                break;
            case baidu_online:
                musicEntityObservable = baiduRequestHandler.handleRequest(id,reference.get().getApplicationContext());
                break;
        }
        return musicEntityObservable;
    }



}
