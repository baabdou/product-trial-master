package com.alten.testTech.payload;

import org.springframework.web.multipart.MultipartFile;

public class ProductRequest {
    private String code;
    private String name;
    private String description;
    private String category;
    private Double price;
    private int quantity;
    private int internalReference;
    private int shellId;
    private String inventoryStatus;
    private double rating;
    private MultipartFile file;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getInternalReference() {
        return internalReference;
    }

    public int getShellId() {
        return shellId;
    }

    public String getInventoryStatus() {
        return inventoryStatus;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setInternalReference(int internalReference) {
        this.internalReference = internalReference;
    }

    public void setShellId(int shellId) {
        this.shellId = shellId;
    }

    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public double getRating() {
        return rating;
    }

    public MultipartFile getFile() {
        return file;
    }
}
