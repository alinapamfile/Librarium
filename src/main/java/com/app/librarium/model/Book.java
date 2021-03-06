package com.app.librarium.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "book")
public class Book extends Item implements Comparable<Book> {
    @Id
    private String id;
    private String ISBN;
    private String title;
    private String author;
    private Integer year;
    private Double rating;
    private Integer quantity;

    public Book(String ISBN, String title, String author, Integer year, Double rating, Integer quantity) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.year = year;
        this.rating = rating;
        this.quantity = quantity;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public Double getRating() {
        return rating;
    }

    @Override
    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public int compareTo(Book o) {
        String obj1 = this.title + " " + this.author;
        String obj2 = o.title + " " + o.author;
        return obj1.compareTo(obj2);
    }
}
