package com.xiefei.openmusicplayer.utils;

/**
 * Created by xiefei-pc on 2016/1/27.
 */
public class Constant {
    public static final String CHOOSE_FLAG="flag";//传递数据时标明获取的key
    public static final String TYPE = "choose_type";//传递的数据属于哪个类型（根据标记获取音乐列表）
    public static final int FILE_TYPE = 1;
    public static final int ALBUM_TYPE = 2;
    public static final int ARTIST_TYPE = 3;
    public static final int ALL_TYPE = 4;
    public static final int SONG_MENU_TYPE = 7;
//    public static final String DATABASE_NAME = "musicinfo.db";
//    public static final String COLLECT_MUSIC_TABLE_NAME = "collect_music";
//    public static final String PLAYED_MUSIC_TABLE_NAME = "played_music";
//    public static final int DATABASE_VERSION = 1;
    public static final String SHARES_NAME = "music_state_save";
    public static final String SAVE_POSITION = "position";
    public static final int STATE_LOOP = 0;
    public static final int STATE_LOOP_ONE = 1;
    public static final int STATE_ORDER = 2;
    public static final int STATE_SHUFFLE = 3;
    public static final String REPEAT_MODE = "repeat_mode";
    public static final String USER_INFO = "user_info";
    public static final String USER_NAME = "user_name";
    public static final int LOAD_HISTORY = 5,LOAD_DOWNLOAD=6,LOAD_COLLECT=7;
    public static final int ONLINE_NEW = 1;//新歌榜
    public static final int ONLINE_HOT = 2;//热歌榜
    public static final int ONLINE_ROCK = 11;//摇滚歌曲
    public static final int ONLINE_POPULAR = 16;//流行曲目
    public static final int ONLINE_CLASSICS = 22;//经典曲目
    public static final int ONLINE_NET = 25;//网络歌曲
    public static final int ONLINE_AME = 23;//情歌对唱
    public static final int ONLINE_FILM = 24;//影视歌曲
    public static final int ONLINE_JAZZ = 12;//爵士
}
