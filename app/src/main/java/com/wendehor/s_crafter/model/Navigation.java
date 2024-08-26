package com.wendehor.s_crafter.model;

import androidx.annotation.NonNull;

public class Navigation {
    int id;
    String title;

    public Navigation(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NonNull
    @Override
    public String toString() {
        return "Navigation{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
