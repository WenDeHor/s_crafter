package com.example.s_crafter.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "story_table")
public class StoryEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String imagePath;
    private String description;
    private boolean isFavorite;
    private int clickCount;

    public StoryEntity(String imagePath, String description, boolean isFavorite, int clickCount) {
        this.imagePath = imagePath;
        this.description = description;
        this.isFavorite = isFavorite;
        this.clickCount = clickCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    @NonNull
    @Override
    public String toString() {
        return "StoryEntity{" +
                "id=" + id +
                ", image=" + imagePath +
                ", description='" + description + '\'' +
                ", isFavorite=" + isFavorite +
                ", clickCount=" + clickCount +
                '}';
    }
}

