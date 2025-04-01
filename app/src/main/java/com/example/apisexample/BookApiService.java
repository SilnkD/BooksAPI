package com.example.apisexample;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface BookApiService {
    @GET("volumes")
    Observable<BookResponse> searchBooks(
            @Query("q") String query,
            @Query("maxResults") int maxResults
    );
}