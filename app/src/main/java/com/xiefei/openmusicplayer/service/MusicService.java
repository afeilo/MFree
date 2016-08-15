package com.xiefei.openmusicplayer.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.xiefei.openmusicplayer.IMediaPlaybackService;
import com.xiefei.openmusicplayer.BuildConfig;
import com.xiefei.openmusicplayer.detail.SongDetailAty;
import com.xiefei.openmusicplayer.entity.SongInfo;
import com.xiefei.openmusicplayer.service.helper.LoadMusicCallBack;
import com.xiefei.openmusicplayer.service.helper.MusicHelper;
import com.xiefei.openmusicplayer.utils.Constant;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by xiefei-pc on 2016/1/24.
 * 功能:针对播放器实现播放功能扩展
 * 可以将该播放器抽象为一台CD机
 * 播放列表抽象为一盘CD
 * 备注：
 * 1.实现播放器的调控（上一首、下一首、暂停、继续、拖取进度、记录播放列表）
 * 2.实现更换CD列表的功能
 * 3.初始化 相当于CD播放器内内置了一张CD光盘（默认是上一次关闭播放器时的播放列表）
 * 4.记录播放列表
 */
public class MusicService extends Service implements LoadMusicCallBack{
    public static final String PLAYSTATE_CHANGED = "com.xiefei.openmusicplay.playstatechanged";
    public static final String ACTION_KEY = "com.xiefei.openmusicplay.actionkey";//用来取action的key
    public static final String NEXT = "com.xiefei.openmusicplay.next";
    public static final String PREV = "com.xiefei.openmusicplay.prev";
    public static final String PAUSE = "com.xiefei.openmusicplay.pause";
    public static final String PLAY = "com.xiefei.openmusicplay.play";
    public static final String STOP = "com.xiefei.openmusicplay.stop";
    public static final String PLAY_CHANGE = "com.xiefei.openmusicplay.playchange";
    public static final String PLAY_LOADING = "com.xiefei.openmusicplay.playloading";//歌曲加载中

    private static final String Tag = "MusicService";
    private static final boolean DEBUG = BuildConfig.DEBUG;
    private MusicHelper musicHelper;
    private Binder serviceStub = null;
    //播放列表,保存播放列表id
    private long playList[];
    private MediaPlayer mediaPlayer;
    private int currentPosition;
    private ServiceMusicEntity currentMusicEntity;//取出当前播放曲目的信息
    private int onlineTag = 0;
    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        serviceStub = new ServiceStub(this);
        musicHelper = new MusicHelper(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if(DEBUG)
            Log.d(Tag,serviceStub.toString());
        return serviceStub;
    }

    public void openFile(String path) {
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param list ids
     * @param position 表示当前内容出于的位置
     * @param onlineTag 表示获取该id的数据库.参考值MusicHelper
     */
    public void open(long[] list, int position,int onlineTag) {
        currentPosition = position;
        this.onlineTag = onlineTag;
        if(playList !=null && playList.length == list.length){
            for (int i = 0; i < list.length; i++) {
                if(playList[i]!=list[i])
                    break;
                if(i == list.length - 1){
                    preparePlay(position);
                    return;
                }
            }
        }
        this.playList = list;
        preparePlay(position);
    }

    public int getQueuePosition() {
        return currentPosition;
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public void stop() {
        notifyChange(PLAYSTATE_CHANGED,STOP);
        mediaPlayer.stop();
    }

    public void pause() {
        notifyChange(PLAYSTATE_CHANGED,PAUSE);
        mediaPlayer.pause();
    }

    public void play() {
        notifyChange(PLAYSTATE_CHANGED,PLAY);
        mediaPlayer.start();
    }

    private void preparePlay(int position){
        mediaPlayer.reset();
        currentPosition = position;
        notifyChange(PLAYSTATE_CHANGED,PLAY_LOADING);
        musicHelper.getMessageById(String.valueOf(playList[position]),onlineTag,this);
    }

    private void notifyChange(String action,String actionWhat){
        Intent intent = new Intent(action);
        intent.putExtra(ACTION_KEY,actionWhat);
        sendBroadcast(intent);
    }
//    private Uri getUriById(long id){
//        return Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, String.valueOf(id));
//    }
    public void prev() {
        int prevPos = getPrev();
        preparePlay(prevPos);
        notifyChange(PLAYSTATE_CHANGED,PREV);
    }

    private int getPrev(){
        int index = currentPosition - 1;
        if(index<0)
            index = playList.length -1;
        return index;
    }
    public void next() {
        preparePlay(getNext());
        notifyChange(PLAYSTATE_CHANGED,NEXT);
    }
    private int getNext(){
        int index = currentPosition + 1;
        if(index>=playList.length)
            index = 0;
        return index;
    }
    public long duration() {
        return mediaPlayer.getDuration();
    }

    public long position() {
        return currentPosition;
    }

    public long seek(long pos) {
        mediaPlayer.seekTo((int) pos);
        return pos;
    }

    public String getTrackName() {
        return currentMusicEntity.getTitle();
    }

    public String getAlbumName() {
        return currentMusicEntity.getAlbum();
    }

    public long getAlbumId() {
        return currentMusicEntity.getId();
    }

    public String getArtistName() {
        return currentMusicEntity.getArtist();
    }

    public long getArtistId() {
        return 0;
    }

    public void enqueue(long[] list, int action) {

    }

    public long[] getQueue() {
        return playList;
    }

    public void moveQueueItem(int from, int to) {

    }

    public void setQueuePosition(int index) {

    }

    public String getPath() {
        return currentMusicEntity.getUrl();
    }

    public long getAudioId() {
        return 0;
    }

    public void setShuffleMode(int shufflemode) {

    }

    public int getShuffleMode() {
        return 0;
    }

    public int removeTracks(int first, int last) {
        return 0;
    }

    public int removeTrack(long id) {
        return 0;
    }

    public void setRepeatMode(int repeatmode) {

    }

    public int getRepeatMode() {
        return 0;
    }

    public int getMediaMountedCount() {
        return 0;
    }

    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void loadSuccess(ServiceMusicEntity entity) {
        currentMusicEntity = entity;
        String uri = entity.getUrl();
        if(DEBUG)
            Log.d(Tag,uri.toString());
        try {
            mediaPlayer.setDataSource(this,Uri.parse(uri));
            //TODO 更新成功.
            mediaPlayer.prepare();
            notifyChange(PLAYSTATE_CHANGED,PLAY_CHANGE);
            play();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void loadFail(Throwable e) {

    }

    //继承IMediaPlaybackService.Stub
    public static class ServiceStub extends IMediaPlaybackService.Stub{
        private WeakReference weakReference = null;

        public ServiceStub(MusicService musicService) {
            weakReference = new WeakReference(musicService);
        }

        @Override

        public void openFile(String path) throws RemoteException {
            ((MusicService)weakReference.get()).openFile(path);
        }

        @Override
        public void open(long[] list, int position,int onlineTag) throws RemoteException {
            ((MusicService)weakReference.get()).open(list,position,onlineTag);
        }

        @Override
        public int getQueuePosition() throws RemoteException {
            return ((MusicService)weakReference.get()).getQueuePosition();
        }

        @Override
        public boolean isPlaying() throws RemoteException {
            return ((MusicService)weakReference.get()).isPlaying();
        }

        @Override
        public void stop() throws RemoteException {
            ((MusicService)weakReference.get()).stop();
        }

        @Override
        public void pause() throws RemoteException {
            ((MusicService)weakReference.get()).pause();
        }

        @Override
        public void play() throws RemoteException {
            ((MusicService)weakReference.get()).play();
        }

        @Override
        public void prev() throws RemoteException {
            ((MusicService)weakReference.get()).prev();
        }

        @Override
        public void next() throws RemoteException {
            ((MusicService)weakReference.get()).next();
        }

        @Override
        public long duration() throws RemoteException {
            return ((MusicService)weakReference.get()).duration();
        }

        @Override
        public long position() throws RemoteException {
            return ((MusicService)weakReference.get()).position();
        }

        @Override
        public long seek(long pos) throws RemoteException {
            return ((MusicService)weakReference.get()).seek(pos);
        }

        @Override
        public String getTrackName() throws RemoteException {
            return ((MusicService)weakReference.get()).getTrackName();
        }

        @Override
        public String getAlbumName() throws RemoteException {
            return ((MusicService)weakReference.get()).getAlbumName();
        }

        @Override
        public long getAlbumId() throws RemoteException {
            return ((MusicService)weakReference.get()).getAlbumId();
        }

        @Override
        public String getArtistName() throws RemoteException {
            return ((MusicService)weakReference.get()).getArtistName();
        }

        @Override
        public long getArtistId() throws RemoteException {
            return ((MusicService)weakReference.get()).getArtistId();
        }

        @Override
        public void enqueue(long[] list, int action) throws RemoteException {
            ((MusicService)weakReference.get()).enqueue(list,action);
        }

        @Override
        public long[] getQueue() throws RemoteException {
            return ((MusicService)weakReference.get()).getQueue();
        }

        @Override
        public void moveQueueItem(int from, int to) throws RemoteException {
            ((MusicService)weakReference.get()).moveQueueItem(from,to);
        }

        @Override
        public void setQueuePosition(int index) throws RemoteException {
            ((MusicService)weakReference.get()).setQueuePosition(index);
        }

        @Override
        public String getPath() throws RemoteException {
            return ((MusicService)weakReference.get()).getPath();
        }

        @Override
        public long getAudioId() throws RemoteException {
            return ((MusicService)weakReference.get()).getAudioId();
        }

        @Override
        public void setShuffleMode(int shufflemode) throws RemoteException {
            ((MusicService)weakReference.get()).setShuffleMode(shufflemode);
        }

        @Override
        public int getShuffleMode() throws RemoteException {
            return ((MusicService)weakReference.get()).getShuffleMode();
        }

        @Override
        public int removeTracks(int first, int last) throws RemoteException {
            return ((MusicService)weakReference.get()).removeTracks(first, last);
        }

        @Override
        public int removeTrack(long id) throws RemoteException {
            return ((MusicService)weakReference.get()).removeTrack(id);
        }

        @Override
        public void setRepeatMode(int repeatmode) throws RemoteException {
            ((MusicService)weakReference.get()).setRepeatMode(repeatmode);
        }

        @Override
        public int getRepeatMode() throws RemoteException {
            return ((MusicService)weakReference.get()).getRepeatMode();
        }

        @Override
        public int getMediaMountedCount() throws RemoteException {
            return ((MusicService)weakReference.get()).getMediaMountedCount();
        }

        @Override
        public int getAudioSessionId() throws RemoteException {
            return ((MusicService)weakReference.get()).getAudioSessionId();
        }
    }
}