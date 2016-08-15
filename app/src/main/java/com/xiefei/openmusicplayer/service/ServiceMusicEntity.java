package com.xiefei.openmusicplayer.service;

/**
 * Created by xiefei on 16/8/14.
 * 服务器端音乐的实体
 */
public class ServiceMusicEntity {
    private long id;
    private String title;//音乐name
    private String album;//专辑名
    private String artist;//歌手
    private long duration;//播放时间
    private String lrcLink;//歌词链接
    private String descPic;//歌曲图片
    private String url;//歌曲播放地址

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getLrcLink() {
        return lrcLink;
    }

    public void setLrcLink(String lrcLink) {
        this.lrcLink = lrcLink;
    }

    public String getDescPic() {
        return descPic;
    }

    public void setDescPic(String descPic) {
        this.descPic = descPic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
