package com.example.apisexample.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.apisexample.data.repository.BookRepository;
import com.example.apisexample.model.Item;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BookViewModel extends ViewModel {
    private final BookRepository repository;
    private final MutableLiveData<List<Item>> books = new MutableLiveData<>();
    private final MutableLiveData<Item> selectedBook = new MutableLiveData<>();
    private final CompositeDisposable disposables = new CompositeDisposable();

    public BookViewModel(BookRepository repository) {
        this.repository = repository;
    }

    public void searchBooks(String query) {
        if (query.isEmpty()) {
            query = "best sellers";
        }

        disposables.add(repository.getBooks(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        booksList -> {
                            Log.d("BookVM", "Books fetched: " + booksList.size());
                            books.postValue(booksList);
                        },
                        throwable -> Log.e("BookVM", "Error fetching books: ", throwable)
                )
        );
    }

    public LiveData<List<Item>> getBooks() {
        return books;
    }

    public LiveData<Item> getSelectedBook() {
        return selectedBook;
    }

    public void selectBook(Item book) {
        selectedBook.setValue(book);
    }

    @Override
    protected void onCleared() {
        disposables.dispose();
        super.onCleared();
    }

    // Кастомная фабрика
    public static class Factory implements ViewModelProvider.Factory {
        private final Application application;

        public Factory(Application application) {
            this.application = application;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(BookViewModel.class)) {
                return (T) new BookViewModel(new BookRepository(application));
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}