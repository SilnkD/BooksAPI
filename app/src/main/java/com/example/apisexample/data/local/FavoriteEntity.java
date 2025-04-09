package com.example.apisexample.data.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites")
public class FavoriteEntity {

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String title;
    private String content;
    private String image;
    private String description;
    private String categories;
    private String isbn;
    private String publisher;
    private String publishedDate;
    private Integer pageCount;
    public FavoriteEntity(String title, String content, String image, String description,
                          String categories, String isbn, String publisher, String publishedDate,
                          Integer pageCount) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.description = description;
        this.categories = categories;
        this.isbn = isbn;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.pageCount = pageCount;
    }

    /*public FavoriteEntity() {
        this.title = "title";
        this.content = "content";
        this.image = "image";
        this.description = "description";
        this.categories = "categories";
        this.isbn = "isbn";
        this.publisher = "publisher";
        this.publishedDate = "publishedDate";
        this.pageCount = 0;
    }*/

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    @NonNull
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategories() { return categories; }
    public void setCategories(String categories) { this.categories = categories; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public String getPublishedDate() { return publishedDate; }
    public void setPublishedDate(String publishedDate) { this.publishedDate = publishedDate; }

    public Integer getPageCount() { return pageCount; }
    public void setPageCount(Integer pageCount) { this.pageCount = pageCount; }
}
