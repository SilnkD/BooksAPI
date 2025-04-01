package com.example.apisexample;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

import java.util.ArrayList;
import java.util.List;
public class BookRepository {
    private final BookApiService apiService;

    public BookRepository() {
        apiService = RetrofitClient.getClient().create(BookApiService.class);
    }

    public Observable<List<Item>> getBooks(String query) {
        return apiService.searchBooks(query, 222)
                .map(response -> convertToItems(response.getItems()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private List<Item> convertToItems(List<BookItem> bookItems) {
        List<Item> items = new ArrayList<>();
        for (BookItem bookItem : bookItems) {
            VolumeInfo volumeInfo = bookItem.getVolumeInfo();
            Item item = new Item();
            item.setTitle(volumeInfo.getTitle());
            item.setContent(formatAuthors(volumeInfo.getAuthors()));

            if (volumeInfo.getImageLinks() != null) {
                item.setImage(volumeInfo.getImageLinks().getThumbnail());
            }

            items.add(item);
        }
        return items;
    }

    private String formatAuthors(List<String> authors) {
        if (authors == null || authors.isEmpty()) return "Unknown author";
        return String.join(", ", authors);
    }
}