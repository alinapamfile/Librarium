package com.app.librarium.model;

import org.springframework.data.annotation.Id;

public class ComicBook {
    @Id
    private String id;
    private String series;
    private Integer number;
    private Integer year;
    private Integer rating;

    public ComicBook(String series, Integer number, Integer year, Integer rating) {
        this.series = series;
        this.number = number;
        this.year = year;
        this.rating = rating;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
