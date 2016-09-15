package com.xiefei.openmusicplayer.service.helper.handler;

import android.content.Context;

import com.xiefei.openmusicplayer.service.ServiceMusicEntity;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by xiefei on 16/8/14.
 */
public abstract class RequestHandler {
    public Observable<ServiceMusicEntity> handleRequest(final long requestId, final Context context){
        return Observable.create(new Observable.OnSubscribe<ServiceMusicEntity>() {
            @Override
            public void call(Subscriber<? super ServiceMusicEntity> subscriber) {
                //构造歌曲信息request
                ServiceMusicEntity entity = getEntity(context,requestId);
                if(entity == null){
                    subscriber.onError(new NoSuchFieldException("获取实例失败"));
                }else {
                    subscriber.onNext(entity);
                }
            }
        });
    }//异步
    abstract ServiceMusicEntity getEntity(Context context,long requestId);//同步
}
