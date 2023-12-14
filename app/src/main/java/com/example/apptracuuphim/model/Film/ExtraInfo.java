package com.example.apptracuuphim.model.Film;

import com.example.apptracuuphim.listener.MiniItemListener;

public class ExtraInfo {
    private String name;
    private String content;
    private MiniItemListener miniItemListener;

    public ExtraInfo(String name, String content) {
        this.name = name;
        this.content = content;
        this.miniItemListener = null;
    }

    public ExtraInfo(String name, String content, MiniItemListener miniItemListener) {
        this.name = name;
        this.content = content;
        this.miniItemListener = miniItemListener;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MiniItemListener getMiniItemListener() {
        return miniItemListener;
    }

    public void setMiniItemListener(MiniItemListener miniItemListener) {
        this.miniItemListener = miniItemListener;
    }
}
