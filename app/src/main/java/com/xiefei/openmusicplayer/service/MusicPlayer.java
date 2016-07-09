package com.xiefei.openmusicplayer.service;


import com.xiefei.openmusicplayer.entity.SongInfo;

/**
 * Created by xiefei on 2016/3/13.
 */
public interface MusicPlayer {
    void setPlayList(int type, String tag);
//    boolean collectMusic();
//    boolean collectMusic(int type, String tag, int position);
//    boolean isCollect();
    void playNext();
    void playLast();
//    void start(int item, int seekSec);
    void start(SongInfo songInfo);
    void pause();
    void goon();
    int changeMode();
    int getRepeatMode();
    void seekTo(long time);
    SongInfo getCurrentMusicInfo();
    void removeListener(MusicListener listener);
    void setListener(MusicListener listener);
    boolean isPlaying();
//    long getDuration();
    public interface MusicListener {
        void playStart();
        void playPause(long second);
        void playGoon();
    }
}
