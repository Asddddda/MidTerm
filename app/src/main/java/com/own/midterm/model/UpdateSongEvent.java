package com.own.midterm.model;

import java.util.List;

/***
 * 用来通过EventBus传数据
 */

public class UpdateSongEvent {
    private List<Song> songList;

    public List<Song> getSongList(){
        return songList;
    }

    public void setSongList(List<Song> songList){
        this.songList = songList;
    }
}
