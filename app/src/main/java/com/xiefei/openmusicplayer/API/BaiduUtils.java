package com.xiefei.openmusicplayer.API;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by xiefei on 16/7/24.
 */
public class BaiduUtils {
    private static String Tag = "OnlineMusicUtil";
//    public static Observable<String> getMusicLrc(final String musicName,final String artistName){
//        return Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext(getIdByKey(musicName,artistName));
//            }
//        }).map(new Func1<String,String>() {
//            @Override
//            public String call(String id) {
////                Log.d("result",id);
//                String lrc = null;
//                if(id!=null)
//                    lrc = getLrcById(id);
//                return lrc;
//            }
//        });
//    }

    public static String getUrlById(String id){
        String resultUrl = null;
        String urls = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=webapp_music&method=baidu.ting.song.downWeb&songid="+id+"&bit=24,%2064,%20128,%20192,%20256,%20320,%20flac";
        try {
            URL url = new URL(urls);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(5000);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            if(connection.getResponseCode()<300){
                int len = -1;
                byte[] bytes = new byte[1024];
                StringBuilder sb = new StringBuilder();
                while ((len = inputStream.read(bytes))!=-1){
                    sb.append(new String(bytes,0,len));
                }
                String s = sb.toString();
                JSONObject jsonObject = new JSONObject(s);
                if(jsonObject.has("bitrate")){
                    JSONArray jsonArray = jsonObject.getJSONArray("bitrate");
                    if(jsonArray.length()>0){
                        resultUrl = jsonArray.getJSONObject(0).getString("file_link");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return resultUrl;
    }
    public static String getLrcById(String id){
        String resultUrl = null;
        String urls = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=webapp_music&method=baidu.ting.song.downWeb&songid="+id+"&bit=24,%2064,%20128,%20192,%20256,%20320,%20flac";
        try {
            URL url = new URL(urls);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(5000);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            if(connection.getResponseCode()<300){
                int len = -1;
                byte[] bytes = new byte[1024];
                StringBuilder sb = new StringBuilder();
                while ((len = inputStream.read(bytes))!=-1){
                    sb.append(new String(bytes,0,len));
                }
                String s = sb.toString();
                JSONObject jsonObj = new JSONObject(s).getJSONObject("songinfo");
//                if(jsonArray.length()>0){
                if(jsonObj.has("lrclink"))
                    resultUrl = jsonObj.getString("lrclink");
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return resultUrl;
    }
    private static String getIdByKey(String musicName,String artistName){
        String resultUrl = null;
        String urls = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=webapp_music&method=baidu.ting.search.catalogSug&query="+ URLEncoder.encode(musicName);
        Log.d(Tag,urls);
        try {
            URL url = new URL(urls);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(5000);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            if(connection.getResponseCode()<300){
                int len = -1;
                byte[] bytes = new byte[1024];
                StringBuilder sb = new StringBuilder();
                while ((len = inputStream.read(bytes))!=-1){
                    sb.append(new String(bytes,0,len));
                }
                String s = sb.toString();
                Log.d(Tag,s);
                JSONObject jsonObject = new JSONObject(s);
                if(jsonObject.has("song")){
                    JSONArray jsonArray = jsonObject.getJSONArray("song");
                    for (int i=0 ;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        if(artistName.equals(jsonObject1.getString("artistname"))){
                            resultUrl = jsonArray.getJSONObject(i).getString("songid");
                            break;
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return resultUrl;
    }
//    private static List<MusicInfo> getData(int type){
//        String urls = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=webapp_music&method=baidu.ting.billboard.billList&type="+type+"&size=20";
//        List<MusicInfo> musicInfos = new ArrayList<>();
//        try {
//            URL url = new URL(urls);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setReadTimeout(5000);
//            connection.connect();
//            InputStream inputStream = connection.getInputStream();
//            if(connection.getResponseCode()<300){
//                int len = -1;
//                byte[] bytes = new byte[1024];
//                StringBuilder sb = new StringBuilder();
//                while ((len = inputStream.read(bytes))!=-1){
//                    sb.append(new String(bytes,0,len));
//                }
//                String s = sb.toString();
//                Log.d(Tag,s);
//                JSONArray jsonArray = new JSONObject(s).getJSONArray("song_list");
//                for (int i = 0;i<jsonArray.length();i++){
//                    MusicInfo musicInfo = new MusicInfo();
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    musicInfo.setId(Integer.parseInt(jsonObject.getString("song_id")));
//                    musicInfo.setTitle(jsonObject.getString("title"));
//                    musicInfo.setArtist(jsonObject.getString("author"));
//                    musicInfos.add(musicInfo);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//        }
//        return musicInfos;
//    }
}
