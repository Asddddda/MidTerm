package com.own.midterm.model;

import java.util.List;

/***
 * 用来通过EventBus传数据
 */

public class UpdateSongEvent {
    private List<Song> songList;

    private String imUrl;

    public String getImUrl() {
        return imUrl;
    }

    public void setImUrl(String imUrl) {
        this.imUrl = imUrl;
    }

    public List<Song> getSongList(){
        return songList;
    }

    public void setSongList(List<Song> songList){
        this.songList = songList;
    }
}
