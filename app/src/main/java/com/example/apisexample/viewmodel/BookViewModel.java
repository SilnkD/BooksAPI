package com.example.apisexample.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.apisexample.data.repository.BookRepository;
import com.example.apisexample.model.Item;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BookViewModel extends AndroidViewModel {

    private final BookRepository repository;
    private final MutableLiveData<List<Item>> books = new MutableLiveData<>();
    private final MutableLiveData<Item> selectedBook = new MutableLiveData<>();
    private final CompositeDisposable disposables = new CompositeDisposable();

    public BookViewModel(@NonNull Application application) {
        super(application);
        repository = new BookRepository(application);
    }

    public void searchBooks(String query) {
        if (query.isEmpty()) query = "best sellers";

        disposables.add(repository.getBooks(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        books::setValue,
                        throwable -> throwable.printStackTrace()
                ));
    }

    public LiveData<List<Item>> getBooks() {
        return books;
    }

    public LiveData<Item> getSelectedBook() {
        return selectedBook;
    }

    public void selectBook(Item item) {
        selectedBook.setValue(item);
    }

    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();
    }
}