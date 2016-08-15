package com.xiefei.openmusicplayer.service.helper.handler;

import android.content.Context;

import com.xiefei.openmusicplayer.service.ServiceMusicEntity;

import rx.Observable;

/**
 * Created by xiefei on 16/8/14.
 */
public abstract class RequestHandler {
    abstract Observable<ServiceMusicEntity> handleRequest(String request,Context context);
}
