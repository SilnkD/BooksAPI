package com.example.apisexample.data.repository;

import com.example.apisexample.data.network.BookApiService;
import com.example.apisexample.data.model.BookItem;
import com.example.apisexample.data.network.RetrofitClient;
import com.example.apisexample.data.model.Item;
import com.example.apisexample.data.model.VolumeInfo;

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
        return apiService.searchBooks(query, 20)
                .map(response -> convertToItems(response.getItems()))
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
            item.setDescription(volumeInfo.getDescription() != null ? volumeInfo.getDescription() : "No description available");
            item.setCategories(volumeInfo.getCategories());

            if (volumeInfo.getIndustryIdentifiers() != null && !volumeInfo.getIndustryIdentifiers().isEmpty()) {
                item.setIsbn(volumeInfo.getIndustryIdentifiers().get(0).getIdentifier());
            }

            if (volumeInfo.getImageLinks() != null && volumeInfo.getImageLinks().getThumbnail() != null) {
                String thumbnailUrl = volumeInfo.getImageLinks().getThumbnail();
                item.setImage(thumbnailUrl.replace("http://", "https://"));
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