package com.example.apisexample.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.apisexample.data.local.BookDatabase;
import com.example.apisexample.data.local.FavoriteDao;
import com.example.apisexample.data.local.FavoriteEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoriteRepository {
    private final FavoriteDao favoriteDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public FavoriteRepository(Application application) {
        BookDatabase db = BookDatabase.getInstance(application);
        favoriteDao = db.favoriteDao();
    }

    public void addToFavorites(FavoriteEntity book) {
        // Лучше вообще не проверять ID вручную, Room сам с этим справится
        executor.execute(() -> favoriteDao.insert(book));
    }

    public void removeFromFavorites(FavoriteEntity book) {
        executor.execute(() -> favoriteDao.delete(book));
    }

    public LiveData<FavoriteEntity> getFavoriteById(Integer id) {
        return favoriteDao.getFavoriteById(id);
    }
    public LiveData<FavoriteEntity> getFavoriteByTitle(String title) {
        return favoriteDao.getFavoriteByTitle(title);
    }

    public LiveData<List<FavoriteEntity>> getFavorites() {
        return favoriteDao.getAllFavorites();
    }
}