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
import android.support.annotation.Nullable;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

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
public class MusicService extends Service implements MediaPlayer.OnCompletionListener,MediaPlayer.OnErrorListener,MusicPlayer
{
    private static String Tag = "MusicService";
    private MediaPlayer mediaPlayer;
    private ArrayList<SongInfo> infos;
    private ArrayList<MusicListener> listeners = new ArrayList<>();
    private ArrayList<SongInfo> collectMusic;
    private int currentPosition = -1;
    private SongInfo currentMusic = null;
//    private TimerThread thread = new TimerThread();
    private boolean isPlaying = false;
    private SharedPreferences preferences;
    private int type;
    private String chooseFlag;
    private int repeatMode;
    private boolean isOnline = false;
    private Random random = new Random();
    private int ONGOING_NOTIFICATION_ID = 100;
    @Override
    public void onCreate() {
        super.onCreate();
        preferences = getSharedPreferences(Constant.SHARES_NAME, MODE_PRIVATE);
        type = preferences.getInt(Constant.TYPE, Constant.ALL_TYPE);
        currentPosition = preferences.getInt(Constant.SAVE_POSITION, 0);
        chooseFlag = preferences.getString(Constant.CHOOSE_FLAG, "null");
        repeatMode = preferences.getInt(Constant.REPEAT_MODE, Constant.STATE_LOOP);
        getMusic(type, chooseFlag);
//        collectMusic = MusicUtil.getInstance(this).getMusicGroupByType(Constant.SONG_MENU_TYPE).get("我的收藏");
        if(infos.size()<=currentPosition){
            getMusic(Constant.ALL_TYPE,null);
        }
        currentMusic = infos.get(currentPosition);
        initNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    public void setListener(MusicListener listener){
        if(!listeners.contains(listener))
            listeners.add(listener);
    }
    public void removeListener(MusicListener listener){
        if(listener!=null&&listeners.contains(listener))
            listeners.remove(listener);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        playNext();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.d(Tag, "发生错误了");
        return false;
    }
    public class MusicBinder extends Binder {
        public MusicPlayer getMusicPlayer(){
            return MusicService.this;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicBinder();
    }
    @Override
    public void seekTo(long time){
//        play(currentPosition, (int) (progress*(infos.get(currentPosition).getDuration())/10000));
            if(mediaPlayer==null)
//                start(currentPosition, (int) (time));
                start(null);
            else {
                mediaPlayer.seekTo((int) (time));
            }
//            thread.setNowSec((int) (time));
    }
    public SongInfo getCurrentMusicInfo(){
        return currentMusic;
    }

    @Override
    public void setPlayList(int type, String tag) {
        if(this.type == type&&chooseFlag==null&&tag==null){
            return;
        } else if(this.type!=type||!chooseFlag.equals(tag)){
            getMusic(type,tag);
        }
    }
    private void getMusic(int type, String tag){
        Log.d(Tag,"getMusic");
        this.type = type;
        chooseFlag = tag;
//        infos = MusicUtil.getInstance(this).getMusicByType(type);
//        if(infos == null){
//            infos = MusicUtil.getInstance(this).getMusicGroupByType(type).get(tag);
//        }
    }
//    @Override
//    public boolean collectMusic() {
//        boolean isCollect = false;
//        if (!isOnline){
//            isCollect = collectMusic.contains(infos.get(currentPosition));
//        if(isCollect){
//            collectMusic.remove(infos.get(currentPosition));
//        }else {
//            collectMusic.add(infos.get(currentPosition));
//        }
//        isCollect = isCollect?false:true;
////        MusicUtil.getInstance(this).saveSongMenu();
//        }
//        return isCollect;
//    }
//
//    @Override
//    public boolean collectMusic(int type, String tag, int position) {
//        ArrayList<SongInfo> musicInfos;
//        if(this.type == type&&chooseFlag==null&&tag==null){
//            musicInfos = infos;
//        } else if(this.type!=type||!chooseFlag.equals(tag)){
//            musicInfos = MusicUtil.getInstance(this).getMusicByType(type);
//            if(musicInfos == null){
//                musicInfos = MusicUtil.getInstance(this).getMusicGroupByType(type).get(tag);
//            }
//        }else {
//            musicInfos = infos;
//        }
//        Log.d(Tag, "currentMusic->" + musicInfos.get(position));
//        if(!collectMusic.contains(musicInfos.get(position))){
//            Log.d(Tag,"add->"+musicInfos.get(position));
//            collectMusic.add(musicInfos.get(position));
//            MusicUtil.getInstance(this).saveCollect();
//        }
//        return false;
//    }
//
//    @Override
//    public boolean isCollect() {
//        return collectMusic.contains(currentMusic);
//    }

    @Override
    public void playNext() {
//            if(repeatMode==Constant.STATE_LOOP){
//                start(currentPosition==infos.size()-1?0:currentPosition+1, 0);
//            }else if(repeatMode==Constant.STATE_LOOP_ONE){
//                start(currentPosition, 0);
//            }else if(repeatMode==Constant.STATE_ORDER){
//                if(currentPosition!=infos.size()-1){
//                    start(currentPosition + 1, 0);
//                }else{
//                    start(currentPosition, 0);
//                }
//            }else {
//                int nextPosition = random.nextInt(infos.size());
//                start(nextPosition, 0);
//            }
    }

    @Override
    public void playLast() {
//            if(repeatMode==Constant.STATE_LOOP){
//                start(currentPosition==0?currentPosition-1+infos.size():currentPosition-1, 0);
//            }else if(repeatMode==Constant.STATE_LOOP_ONE){
//                start(currentPosition, 0);
//            }else if(repeatMode==Constant.STATE_ORDER){
//                if(currentPosition!=0){
//                    start(currentPosition - 1, 0);
//                }else{
//                    start(currentPosition, 0);
//                }
//            }else {
//                int nextPosition = random.nextInt(infos.size());
//                start(nextPosition,0);
//            }
    }

//    @Override
//    public void start(int item, int seekSec) {
//        isOnline = false;
//        if(type != Constant.LOAD_HISTORY){
//            MusicUtil.getInstance(this).addHistory(infos.get(item));
//        }
//            currentPosition = item;
//        if(infos.size()<currentPosition){
//            getMusic(Constant.ALL_TYPE,null);
//        }
//            if(mediaPlayer==null){
//                mediaPlayer = new MediaPlayer();
//                mediaPlayer.setOnCompletionListener(this);
//            }
//            if(infos!=null){
//                try {
//                    isPlaying = true;
//                    mediaPlayer.reset();
//                    mediaPlayer.setDataSource(this, Uri.parse(infos.get(item).getUrl()));
////                mediaPlayer.setDataSource(this, Uri.parse("/storage/emulated/0/Music/周深-好想你【男声合唱】.mp3"));
//                    mediaPlayer.prepare();
//                    mediaPlayer.seekTo(seekSec);
//                    mediaPlayer.start();
//                    thread.reset();
//                    thread.setNowSec((int) (seekSec * (infos.get(currentPosition).getDuration()) / 10000));
//                    currentMusic = infos.get(currentPosition);
//                    currentMusic.setDuration(mediaPlayer.getDuration());
//                    for (MusicListener listener:listeners) {
//                        listener.playStart(currentMusic);
//                    }
//                    if(!thread.isAlive()){
//                        thread.start();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            saveState();
//    }

    @Override
    public void start(SongInfo songInfo) {
        currentMusic = songInfo;
        for (MusicListener listener:listeners) {
            listener.playStart();
        }
        if(isOnline){
            isPlaying = true;
            mediaPlayer.start();
            return;
        }
//        thread.reset();
//        if(!thread.isAlive()){
//            thread.start();
//        }
        if(mediaPlayer==null){
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(this);
        }
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(this, Uri.parse(songInfo.getUrl()));
//                mediaPlayer.setDataSource(this, Uri.parse("/storage/emulated/0/Music/周深-好想你【男声合唱】.mp3"));
                mediaPlayer.prepare();
                mediaPlayer.start();
                currentMusic.setDuration(mediaPlayer.getDuration());
//                thread.reset();
                isPlaying = true;
                isOnline = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void pause(){
        isPlaying = false;
        mediaPlayer.pause();
//        thread.setIsPause(true);
        for (MusicListener listener:listeners) {
            listener.playPause(mediaPlayer.getCurrentPosition());
        }
    }

    @Override
    public int changeMode() {
        repeatMode = (repeatMode+1)%4;
        setRepeatMode(repeatMode);
        return repeatMode;
    }

    @Override
    public int getRepeatMode() {
        return repeatMode;
    }

    public void goon(){
        if(isOnline){
            mediaPlayer.start();
            start(currentMusic);
//            for (MusicListener listener:listeners) {
//                listener.playStart(currentMusic);
//            }
//            isPlaying = true;
            return;
        }
        if(infos!=null&&infos.size()!=0){
            isPlaying = true;
            if(mediaPlayer==null){
//                start();
                return;
            }
            for (MusicListener listener:listeners) {
                listener.playGoon();
            }
            mediaPlayer.start();
//            thread.setIsPause(false);
        }
    }
    public boolean isPlaying(){
        return isPlaying;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
//        pause();
        saveState();
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    Handler handler = new Handler();//主要用来主线程通信的
    //新线程 用来更新进度。
//    class TimerThread extends Thread {
//        long nowSec = 0;
//        long sec = 0;
//        boolean isPause = false;
//        @Override
//        public void run() {
//            while (true){
//                try {
//                    Thread.sleep(200);
//                    if(!isPause){
//                        nowSec+=200;
//                        for (final MusicListener listener:listeners) {
//                            handler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    listener.playProgress((int) (nowSec * 10000/sec));
//                                }
//                            });
//                        }
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        public void setIsPause(boolean isPause) {
//            this.isPause = isPause;
//        }
//
//        public void reset(){
//            nowSec = 0;
//            sec = currentMusic.getDuration();
//        }
//        public void setNowSec(long sec){
//            nowSec = sec;
//        }
//    }
    private void saveState(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constant.TYPE,type);
        editor.putInt(Constant.SAVE_POSITION,currentPosition);
        editor.putString(Constant.CHOOSE_FLAG, chooseFlag);
        editor.commit();
    }

    public void setRepeatMode(int repeatMode) {
        this.repeatMode = repeatMode;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constant.REPEAT_MODE,repeatMode);
        editor.commit();
    }
    private void initNotification(){
        Intent notificationIntent = new Intent(this,SongDetailAty.class);
        PendingIntent intent = PendingIntent.getActivity(this,0,notificationIntent,0);
        Intent resultIntent = new Intent(this, SongDetailAty.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(SongDetailAty.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
//        Notification notification = new NotificationCompat.Builder(MusicService.this).setContentIntent(resultPendingIntent).setContentInfo("123").setContentTitle("345").setContent(new RemoteViews(getPackageName(), R.layout.player_bar)).getNotification();
//        startForeground(ONGOING_NOTIFICATION_ID,notification);
//        NotificationManagerCompat.from(this).notify(ONGOING_NOTIFICATION_ID,notification);
    }
}
