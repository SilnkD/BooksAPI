package com.example.apisexample.data.repository;

import android.app.Application;

import com.example.apisexample.data.remote.BookApiService;
import com.example.apisexample.model.BookItem;
import com.example.apisexample.data.remote.RetrofitClient;
import com.example.apisexample.model.Item;
import com.example.apisexample.model.VolumeInfo;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class BookRepository {
    private final BookApiService apiService;

    public BookRepository(Application application) {
        apiService = RetrofitClient.getClient().create(BookApiService.class);
    }

    public Observable<List<Item>> getBooks(String query) {
        return apiService.searchBooks(query, 20)
                .map(response -> {
                    if (response == null || response.getItems() == null) {
                        return new ArrayList<Item>();
                    }
                    return convertToItems(response.getItems());
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    private List<Item> convertToItems(List<BookItem> bookItems) {
        List<Item> items = new ArrayList<>();
        for (BookItem bookItem : bookItems) {
            VolumeInfo volumeInfo = bookItem.getVolumeInfo();
            if (volumeInfo == null) continue;

            Item item = new Item();
            item.setTitle(volumeInfo.getTitle() != null ? volumeInfo.getTitle() : "No title");
            item.setContent(formatAuthors(volumeInfo.getAuthors()));
            item.setPublisher(volumeInfo.getPublisher());
            item.setPublishedDate(volumeInfo.getPublishedDate());
            item.setPageCount(volumeInfo.getPageCount());
            item.setDescription(volumeInfo.getDescription() != null ? volumeInfo.getDescription() : "No description");
            item.setCategories(volumeInfo.getCategories());

            if (volumeInfo.getIndustryIdentifiers() != null && !volumeInfo.getIndustryIdentifiers().isEmpty()) {
                item.setIsbn(volumeInfo.getIndustryIdentifiers().get(0).getIdentifier());
            }

            if (volumeInfo.getImageLinks() != null && volumeInfo.getImageLinks().getThumbnail() != null) {
                item.setImage(volumeInfo.getImageLinks().getThumbnail().replace("http://", "https://"));
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