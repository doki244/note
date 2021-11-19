package com.example.note_paad;

public class note_modle {
    private String voice_path,text,time,title;

    public note_modle(String voice_path, String text, String time, String title) {
        this.voice_path = voice_path;
        this.text = text;
        this.time = time;
        this.title = title;
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
                '}';
    }}
