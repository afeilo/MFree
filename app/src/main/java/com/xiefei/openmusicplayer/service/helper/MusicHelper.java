package com.xiefei.openmusicplayer.service.helper;

import android.content.Context;
import android.util.Log;

import com.xiefei.openmusicplayer.entity.MusicPlaybackTrack;
import com.xiefei.openmusicplayer.service.ServiceMusicEntity;
import com.xiefei.openmusicplayer.service.helper.handler.BaiduRequestHandler;
import com.xiefei.openmusicplayer.service.helper.handler.LocalRequestHandler;
import com.xiefei.openmusicplayer.utils.OpenMusicPlayerUtils;

import java.lang.ref.WeakReference;

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
//    public static final int local = OpenMusicPlayerUtils.IdType.Location.mId;//本地音乐
//    public static final int file = 0;//本地音乐
//    public static final int baidu_online = OpenMusicPlayerUtils.IdType.Baidu.mId;//百度音乐接口
    private WeakReference<Context> reference;
    private final LocalRequestHandler localRequestHandler = new LocalRequestHandler();
    private final BaiduRequestHandler baiduRequestHandler = new BaiduRequestHandler();
    public MusicHelper(Context context){
        if(reference == null)
            reference = new WeakReference<>(context);
    }
    private Observable<ServiceMusicEntity> musicEntityObservable;
    public void getMessageById(MusicPlaybackTrack track, final LoadMusicCallBack callBack){
//        if(musicEntityObservable!=null);
        musicEntityObservable  = getEntityObservable(track.mId, track.mSourceType);
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
    public ServiceMusicEntity getMessageById(MusicPlaybackTrack track){
//        if(musicEntityObservable!=null);
        switch (track.mSourceType){
            case Location:
                return  localRequestHandler.getEntity(reference.get().getApplicationContext(),track.mId);
            case Baidu:
                return  baiduRequestHandler.getEntity(reference.get().getApplicationContext(),track.mId);
        }
        return null;
    }

    private Observable<ServiceMusicEntity> getEntityObservable(long id, OpenMusicPlayerUtils.IdType IdType) {
        Observable<ServiceMusicEntity> musicEntityObservable = null;
        switch (IdType){
            case Location:
                musicEntityObservable = localRequestHandler.handleRequest(id,reference.get().getApplicationContext());
                break;
            case Baidu:
                musicEntityObservable = baiduRequestHandler.handleRequest(id,reference.get().getApplicationContext());
                break;
        }
        return musicEntityObservable;
    }



}
