package com.xiefei.openmusicplayer.entity;

/**
 * Created by xiefei on 16/6/30.
 */
public class Artist {
    private long id;
    private String astist;
    private int numberOfAlbums;
    private int numberOfTracks;

    public Artist(long id, String astist, int numberOfAlbums, int numberOfTracks) {
        this.astist = astist;
        this.id = id;
        this.numberOfAlbums = numberOfAlbums;
        this.numberOfTracks = numberOfTracks;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAstist() {
        return astist;
    }

    public void setAstist(String astist) {
        this.astist = astist;
    }

    public int getNumberOfAlbums() {
        return numberOfAlbums;
    }

    public void setNumberOfAlbums(int numberOfAlbums) {
        this.numberOfAlbums = numberOfAlbums;
    }

    public int getNumberOfTracks() {
        return numberOfTracks;
    }

    public void setNumberOfTracks(int numberOfTracks) {
        this.numberOfTracks = numberOfTracks;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", astist='" + astist + '\'' +
                ", numberOfAlbums=" + numberOfAlbums +
                ", numberOfTracks=" + numberOfTracks +
                '}';
    }
}
