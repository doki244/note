package com.example.note_paad;

import androidx.annotation.Nullable;

public class note_modle {
    private String voice_path,text,time,title,subtitle;
    private int id;
    private byte[] image;

    public note_modle(@Nullable String voice_path, String text, String time, String title,@Nullable String subtitle,@Nullable byte[] image) {
        this.voice_path = voice_path;
        this.text = text;
        this.time = time;
        this.title = title;
        this.subtitle = subtitle;
        this.image = image;
    }
    public note_modle(@Nullable String voice_path, String text, String time, String title,@Nullable String subtitle, @Nullable byte[] image, int id) {
        this.voice_path = voice_path;
        this.text = text;
        this.time = time;
        this.title = title;
        this.subtitle = subtitle;
        this.id = id;
        this.image = image;
    }
    public int getId() {
        return id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getVoice_path() {
        return voice_path;
    }

    public void setVoice_path(String voice_path) {
        this.voice_path = voice_path;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "note_modle{" +
                "voice_path='" + voice_path + '\'' +
                ", text='" + text + '\'' +
                ", time='" + time + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                '}';
    }
}
