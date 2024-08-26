package com.wendehor.s_crafter.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.wendehor.s_crafter.model.StoryEntity;

import java.util.List;

@Dao
public interface StoryDao {
    @Insert
    void insert(StoryEntity storyEntity);

    @Update
    void update(StoryEntity storyEntity);

    @Query("DELETE FROM story_table WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT * FROM story_table ORDER BY id DESC")
    LiveData<List<StoryEntity>> getAllImages();

    @Query("SELECT * FROM story_table WHERE isFavorite = 1 LIMIT 1")
    StoryEntity getFavoriteImage();

    @Query("UPDATE story_table SET clickCount = clickCount + 1 WHERE id = :id")
    void incrementClickCount(int id);
}
