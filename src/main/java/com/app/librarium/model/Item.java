package com.app.librarium.model;

public abstract class Item {
    public abstract String getId();
    public abstract void setId(String id);
    public abstract Double getRating();
    public abstract void setRating(Double rating);
    public abstract Integer getQuantity();
    public abstract void setQuantity(Integer quantity);
}
