package com.example.apptracuuphim.model.Film;

import android.widget.CheckBox;

public class CheckBoxModel {
    private String title;
    private android.widget.CheckBox lastChecked ;
    private int lastCheckedPos;
    public CheckBoxModel(){
        this.title = "";
        this.lastChecked  = null;
        lastCheckedPos = 0;
    }
    public CheckBoxModel(String title, android.widget.CheckBox lastChecked , int lastCheckedPos) {
        this.title = title;
        this.lastChecked  = lastChecked;
        this.lastCheckedPos = lastCheckedPos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CheckBox getLastChecked() {
        return lastChecked;
    }

    public void setLastChecked(CheckBox lastChecked) {
        this.lastChecked = lastChecked;
    }

    public int getLastCheckedPos() {
        return lastCheckedPos;
    }

    public void setLastCheckedPos(int lastCheckedPos) {
        this.lastCheckedPos = lastCheckedPos;
    }
}
