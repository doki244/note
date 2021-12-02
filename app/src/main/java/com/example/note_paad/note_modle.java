package com.example.note_paad;

import androidx.annotation.Nullable;

public class note_modle {
    private String voice_path,text,time,title,subtitle;
    private int id;
    private String image_path;

    public note_modle(@Nullable String voice_path, String text, String time, String title,@Nullable String subtitle,@Nullable String image_path) {
        this.voice_path = voice_path;
        this.text = text;
        this.time = time;
        this.title = title;
        this.subtitle = subtitle;
        this.image_path = image_path;
    }
    public note_modle(@Nullable String voice_path, String text, String time, String title,@Nullable String subtitle, @Nullable String image_path, int id) {
        this.voice_path = voice_path;
        this.text = text;
        this.time = time;
        this.title = title;
        this.subtitle = subtitle;
        this.id = id;
        this.image_path = image_path;
    }
    public int getId() {
        return id;
    }

    public String getImage() {
        return image_path;
    }

    public void setImage(String image_path) {
        this.image_path = image_path;
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
