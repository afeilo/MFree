package com.xiefei.openmusicplayer.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.xiefei.openmusicplayer.IMediaPlaybackService;
import com.xiefei.openmusicplayer.detail.SongDetailAty;
import com.xiefei.openmusicplayer.entity.SongInfo;
import com.xiefei.openmusicplayer.utils.Constant;

import java.io.IOException;
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void openFile(String path) {

    }

    @Override
    public void open(long[] list, int position) {

    }

    @Override
    public int getQueuePosition() {
        return 0;
    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public void stop() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void play() {

    }

    @Override
    public void prev() {

    }

    @Override
    public void next() {

    }

    @Override
    public long duration() {
        return 0;
    }

    @Override
    public long position() {
        return 0;
    }

    @Override
    public long seek(long pos) {
        return 0;
    }

    @Override
    public String getTrackName() {
        return null;
    }

    @Override
    public String getAlbumName() {
        return null;
    }

    @Override
    public long getAlbumId() {
        return 0;
    }

    @Override
    public String getArtistName() {
        return null;
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
        return new long[0];
    }

    @Override
    public void moveQueueItem(int from, int to) {

    }

    @Override
    public void setQueuePosition(int index) {

    }

    @Override
    public String getPath() {
        return null;
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
    //继承IMediaPlaybackService.Stub
    public static class ServiceStub extends IMediaPlaybackService.Stub{

        @Override
        public void openFile(String path) throws RemoteException {

        }

        @Override
        public void open(long[] list, int position) throws RemoteException {

        }

        @Override
        public int getQueuePosition() throws RemoteException {
            return 0;
        }

        @Override
        public boolean isPlaying() throws RemoteException {
            return false;
        }

        @Override
        public void stop() throws RemoteException {

        }

        @Override
        public void pause() throws RemoteException {

        }

        @Override
        public void play() throws RemoteException {

        }

        @Override
        public void prev() throws RemoteException {

        }

        @Override
        public void next() throws RemoteException {

        }

        @Override
        public long duration() throws RemoteException {
            return 0;
        }

        @Override
        public long position() throws RemoteException {
            return 0;
        }

        @Override
        public long seek(long pos) throws RemoteException {
            return 0;
        }

        @Override
        public String getTrackName() throws RemoteException {
            return null;
        }

        @Override
        public String getAlbumName() throws RemoteException {
            return null;
        }

        @Override
        public long getAlbumId() throws RemoteException {
            return 0;
        }

        @Override
        public String getArtistName() throws RemoteException {
            return null;
        }

        @Override
        public long getArtistId() throws RemoteException {
            return 0;
        }

        @Override
        public void enqueue(long[] list, int action) throws RemoteException {

        }

        @Override
        public long[] getQueue() throws RemoteException {
            return new long[0];
        }

        @Override
        public void moveQueueItem(int from, int to) throws RemoteException {

        }

        @Override
        public void setQueuePosition(int index) throws RemoteException {

        }

        @Override
        public String getPath() throws RemoteException {
            return null;
        }

        @Override
        public long getAudioId() throws RemoteException {
            return 0;
        }

        @Override
        public void setShuffleMode(int shufflemode) throws RemoteException {

        }

        @Override
        public int getShuffleMode() throws RemoteException {
            return 0;
        }

        @Override
        public int removeTracks(int first, int last) throws RemoteException {
            return 0;
        }

        @Override
        public int removeTrack(long id) throws RemoteException {
            return 0;
        }

        @Override
        public void setRepeatMode(int repeatmode) throws RemoteException {

        }

        @Override
        public int getRepeatMode() throws RemoteException {
            return 0;
        }

        @Override
        public int getMediaMountedCount() throws RemoteException {
            return 0;
        }

        @Override
        public int getAudioSessionId() throws RemoteException {
            return 0;
        }
    }
}