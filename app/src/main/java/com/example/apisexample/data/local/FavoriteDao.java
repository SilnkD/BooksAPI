package com.example.apisexample.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteEntity favorite);

    @Delete
    void delete(FavoriteEntity favorite);

    @Query("SELECT * FROM favorites")
    LiveData<List<FavoriteEntity>> getAllFavorites();

    @Query("SELECT * FROM favorites WHERE id = :id LIMIT 1")
    LiveData<FavoriteEntity> getFavoriteById(Integer id);
    @Query("SELECT * FROM favorites WHERE title = :title LIMIT 1")
    LiveData<FavoriteEntity> getFavoriteByTitle(String title);

}