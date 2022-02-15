package com.example.note_paad;

public class theme {
    int background;
     int item;
     int text_color;
     int item_text_color;
     int FloatingActionButton;
     int creat_background;

    public theme(int background, int item, int text_color, int item_text_color, int floatingActionButton, int creat_background) {
        this.background = background;
        this.item = item;
        this.text_color = text_color;
        this.item_text_color = item_text_color;
        FloatingActionButton = floatingActionButton;
        this.creat_background = creat_background;
    }

    public int getBackground() {
        return background;
    }

    public int getItem() {
        return item;
    }

    public int getText_color() {
        return text_color;
    }

    public int getItem_text_color() {
        return item_text_color;
    }

    public int getFloatingActionButton() {
        return FloatingActionButton;
    }

    public int getCreat_background() {
        return creat_background;
    }
}
