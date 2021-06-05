package com.example.challenge02.Database;

import org.litepal.crud.DataSupport;

public class Img extends DataSupport {

    private int currentPosition;
    private String img;

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
