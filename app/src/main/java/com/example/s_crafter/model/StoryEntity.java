package com.example.s_crafter.model;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "story_table")
public class StoryEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String imagePath;  // Шлях до зображення
    private String description;  // Опис або текст
    private boolean isFavorite;  // Позначення улюбленої картинки
    private int clickCount;  // Лічильник кліків

    // Конструктор
    public StoryEntity(String imagePath, String description, boolean isFavorite, int clickCount) {
        this.imagePath = imagePath;
        this.description = description;
        this.isFavorite = isFavorite;
        this.clickCount = clickCount;
    }

    // Getters and Setters (можна автоматично згенерувати)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
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
}


