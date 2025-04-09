package com.example.apisexample.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.apisexample.data.local.FavoriteEntity;
import com.example.apisexample.data.repository.FavoriteRepository;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {
    private FavoriteRepository repository;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        repository = new FavoriteRepository(application);
    }

    public void addToFavorites(FavoriteEntity book) {
        repository.addToFavorites(book);
    }

    public void removeFromFavorites(FavoriteEntity book) {
        repository.removeFromFavorites(book);
    }

    public LiveData<List<FavoriteEntity>> getFavorites(int id) {
        return repository.getFavorites();
    }
    public LiveData<FavoriteEntity> getFavoriteById(Integer id) {
        return repository.getFavoriteById(id);
    }
    public LiveData<FavoriteEntity> getFavoriteByTitle(String title) {
        return repository.getFavoriteByTitle(title);
    }

}
