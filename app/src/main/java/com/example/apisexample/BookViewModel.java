package com.example.apisexample;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Collections;
import java.util.List;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class BookViewModel extends ViewModel {
    private final BookRepository repository = new BookRepository();
    private final MutableLiveData<List<Item>> books = new MutableLiveData<>();
    private final CompositeDisposable disposables = new CompositeDisposable();

    public void searchBooks(String query) {
        if (query.isEmpty()) {
            query = "best sellers";
        }
        disposables.add(repository.getBooks(query)
                .subscribe(
                        books::postValue,
                        throwable -> Log.e("BookVM", "Error: ", throwable)
                ));
    }

    public LiveData<List<Item>> getBooks() {
        return books;
    }

    @Override
    protected void onCleared() {
        disposables.dispose();
        super.onCleared();
    }
}