package com.xiefei.openmusicplayer.service.helper.handler;

import android.content.Context;

import com.xiefei.openmusicplayer.service.ServiceMusicEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by xiefei on 16/8/14.
 */
public class BaiduRequestHandler extends RequestHandler{
    @Override
    public Observable<ServiceMusicEntity> handleRequest(final String request, Context context) {
        return Observable.create(new Observable.OnSubscribe<ServiceMusicEntity>() {
            @Override
            public void call(Subscriber<? super ServiceMusicEntity> subscriber) {
                //构造歌曲信息request
                String url = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.8.1.0&method=baidu.ting.song.getInfos&format=json&songid="+
                        request+"&e=X9geOVmCaT0sj62uoeOhIfl7NHB6%2BX7cfRzY%2Fj4opNA%3D";
                OkHttpClient okHttpClient = new OkHttpClient();
                Request okHttpRequest = new Request.Builder().url(url).build();
                try {
                    Response response = okHttpClient.newCall(okHttpRequest).execute();
                    String json = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        if(jsonObject.has("error_code")){
                            if(jsonObject.getInt("error_code")==22000){
                                ServiceMusicEntity entity = new ServiceMusicEntity();
                                JSONObject jsonobj = jsonObject.getJSONObject("songurl").getJSONArray("url").getJSONObject(0);
                                entity.setUrl(jsonobj.getString("show_link"));
                                if(jsonObject.has("songinfo")){
                                    jsonObject = jsonObject.getJSONObject("songinfo");
                                    entity.setTitle(jsonObject.getString("title"));
                                    entity.setArtist(jsonObject.getString("author"));
                                    entity.setAlbum(jsonObject.getString("album_title"));
                                    entity.setId(jsonObject.getLong("song_id"));
                                    entity.setDescPic(jsonObject.getString("album_500_500"));
                                    subscriber.onNext(entity);
                                    return;
                                }
                            }
                        }
                        subscriber.onError(new Exception("获取数据失败"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        subscriber.onError(e);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
    }
}
