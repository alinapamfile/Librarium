package com.app.librarium.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "rental")
public class Rental {
    @Id
    private String id;
    private String itemId;
    private String userId;
    private LocalDate rentedDate;
    private LocalDate returnedDate;

    public Rental(String itemId, String userId, LocalDate rentedDate, LocalDate returnedDate) {
        this.itemId = itemId;
        this.userId = userId;
        this.rentedDate = rentedDate;
        this.returnedDate = returnedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDate getRentedDate() {
        return rentedDate;
    }

    public void setRentedDate(LocalDate rentedDate) {
        this.rentedDate = rentedDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }
}
