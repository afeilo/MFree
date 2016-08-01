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
import com.xiefei.openmusicplayer.detail.SongDetailAty;
import com.xiefei.openmusicplayer.entity.SongInfo;
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
public class MusicService extends Service implements MusicPlayer{
    public static final String PLAYSTATE_CHANGED = "com.xiefei.openmusicplay.playstatechanged";
    public static final String ACTION_KEY = "com.xiefei.openmusicplay.actionkey";//用来取action的key
    public static final String NEXT = "com.xiefei.openmusicplay.next";
    public static final String PREV = "com.xiefei.openmusicplay.prev";
    public static final String PAUSE = "com.xiefei.openmusicplay.pause";
    public static final String PLAY = "com.xiefei.openmusicplay.play";
    public static final String STOP = "com.xiefei.openmusicplay.stop";
    public static final String PLAY_CHANGE = "com.xiefei.openmusicplay.playchange";

    private static String Tag = MusicService.class.getName();
    private Binder serviceStub = null;
    //播放列表,保存播放列表id
    private long playList[];
    private MediaPlayer mediaPlayer;
    private int currentPosition;
    private Cursor mCursor;//取出当前播放曲目的信息

    String mediaProject[] = new String[]{MediaStore.Audio.Media._ID,MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DURATION,MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM_ID};
    private static final int titleIndex = 1;
    private static final int artistIndex = 2;
    private static final int albumIndex = 3;
    private static final int durationIndex = 4;
    private static final int dataIndex = 5;
    private static final int albumIdndex = 6;
    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        serviceStub = new ServiceStub(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return serviceStub;
    }

    @Override
    public void openFile(String path) {
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void open(long[] list, int position) {
        currentPosition = position;
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

    @Override
    public int getQueuePosition() {
        return currentPosition;
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public void stop() {
        notifyChange(PLAYSTATE_CHANGED,STOP);
        mediaPlayer.stop();
    }

    @Override
    public void pause() {
        notifyChange(PLAYSTATE_CHANGED,PAUSE);
        mediaPlayer.pause();
    }

    @Override
    public void play() {
        notifyChange(PLAYSTATE_CHANGED,PLAY);
        mediaPlayer.start();
    }

    private void preparePlay(int position){
        try {
            mediaPlayer.reset();
            Uri uri = getUriById(playList[position]);
            Log.d(Tag,uri.toString());
            mediaPlayer.setDataSource(this,uri);
            updateMediaCursor(MediaStore.Audio.Media._ID +"="+playList[position]);
            //TODO 更新成功.
            currentPosition = position;
            mediaPlayer.prepare();
            notifyChange(PLAYSTATE_CHANGED,PLAY_CHANGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void updateMediaCursor(String selection){
        if(mCursor != null){
            mCursor.close();
            mCursor = null;
        }
        mCursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,mediaProject,selection,null,null);
        mCursor.moveToFirst();
    }
    private void notifyChange(String action,String actionWhat){
        Intent intent = new Intent(action);
        intent.putExtra(ACTION_KEY,actionWhat);
        sendBroadcast(intent);
    }
    private Uri getUriById(long id){
        return Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, String.valueOf(id));
    }
    @Override
    public void prev() {
        int prevPos = getPrev();
        preparePlay(prevPos);
        notifyChange(PLAYSTATE_CHANGED,PREV);
        play();
    }

    private int getPrev(){
        int index = currentPosition - 1;
        if(index<0)
            index = playList.length -1;
        return index;
    }
    @Override
    public void next() {
        preparePlay(getNext());
        notifyChange(PLAYSTATE_CHANGED,NEXT);
        play();
    }
    private int getNext(){
        int index = currentPosition + 1;
        if(index>=playList.length)
            index = 0;
        return index;
    }
    @Override
    public long duration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public long position() {
        return currentPosition;
    }

    @Override
    public long seek(long pos) {
        mediaPlayer.seekTo((int) pos);
        return pos;
    }

    @Override
    public String getTrackName() {
        return mCursor.getString(titleIndex);
    }

    @Override
    public String getAlbumName() {
        return mCursor.getString(albumIndex);
    }

    @Override
    public long getAlbumId() {
        return mCursor.getLong(albumIdndex);
    }

    @Override
    public String getArtistName() {
        return mCursor.getString(artistIndex);
    }

    @Override
    public long getArtistId() {
        return 0;
    }

    @Override
    public void enqueue(long[] list, int action) {

    }

    @Override
    public long[] getQueue() {
        return playList;
    }

    @Override
    public void moveQueueItem(int from, int to) {

    }

    @Override
    public void setQueuePosition(int index) {

    }

    @Override
    public String getPath() {
        return mCursor.getString(dataIndex);
    }

    @Override
    public long getAudioId() {
        return 0;
    }

    @Override
    public void setShuffleMode(int shufflemode) {

    }

    @Override
    public int getShuffleMode() {
        return 0;
    }

    @Override
    public int removeTracks(int first, int last) {
        return 0;
    }

    @Override
    public int removeTrack(long id) {
        return 0;
    }

    @Override
    public void setRepeatMode(int repeatmode) {

    }

    @Override
    public int getRepeatMode() {
        return 0;
    }

    @Override
    public int getMediaMountedCount() {
        return 0;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mCursor!=null){
            mCursor.close();
            mCursor = null;
        }
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
        public void open(long[] list, int position) throws RemoteException {
            ((MusicService)weakReference.get()).open(list,position);
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