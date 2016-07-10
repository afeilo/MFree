package com.xiefei.openmusicplayer.entity;

import java.io.Serializable;

/**
 * Created by xiefei-pc on 2016/1/24.
 */
public class SongInfo implements Serializable {
    private long id;
    private String title;//音乐name
    private String album;//专辑名
    private String artist;//歌手
    private long duration;//播放时间
    private String url;//地址
    private long albumId;//专辑id
    public SongInfo(long id, String title, String album,long albumId,String artist, long duration, String url) {
        this.id = id;
        this.title = title;
        this.album = album;
        this.albumId = albumId;
        this.artist = artist;
        this.duration = duration;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
//
//    public String getData() {
//        return data;
//    }
//
//    public void setData(String data) {
//        this.data = data;
//    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    @Override
    public String toString() {
        return "SongInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", album='" + album + '\'' +
                ", artist='" + artist + '\'' +
                ", duration=" + duration +
                ", url='" + url + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongInfo songInfo = (SongInfo) o;
        return !(url != null ? !url.equals(songInfo.url) : songInfo.url != null);

    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }
}
