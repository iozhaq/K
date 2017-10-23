package com.kaishengit.entity;

public class Product {

    private Long id;
    private String productName;
    private Long productInventory;

    public Product() {}

    public Product(String productName, Long productInventory) {
        this.productName = productName;
        this.productInventory = productInventory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductInventory() {
        return productInventory;
    }

    public void setProductInventory(Long productInventory) {
        this.productInventory = productInventory;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productInventory=" + productInventory +
                '}';
    }
}
