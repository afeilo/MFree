package com.xiefei.openmusicplayer.service.helper;

import com.xiefei.openmusicplayer.service.ServiceMusicEntity;

/**
 * Created by xiefei on 16/8/15.
 */
public interface LoadMusicCallBack {
    void loadSuccess(ServiceMusicEntity entity);
    void loadFail(Throwable e);
}
