package com.Models;

import javafx.beans.property.SimpleStringProperty;

public class Article {

    SimpleStringProperty label, nArticle, minStock, price;

    public Article(String nArticle, String label, String price, String minStock) {
        this.label = new SimpleStringProperty(label);
        this.nArticle = new SimpleStringProperty(nArticle);
        this.minStock = new SimpleStringProperty(minStock);
        this.price = new SimpleStringProperty(price);
    }

    //region Getters & Setters
    public String getLabel() {
        return label.get();
    }

    public SimpleStringProperty labelProperty() {
        return label;
    }

    public void setLabel(String label) {
        this.label.set(label);
    }

    public String getnArticle() {
        return nArticle.get();
    }

    public SimpleStringProperty nArticleProperty() {
        return nArticle;
    }

    public void setnArticle(String nArticle) {
        this.nArticle.set(nArticle);
    }

    public String getMinStock() {
        return minStock.get();
    }

    public SimpleStringProperty minStockProperty() {
        return minStock;
    }

    public void setMinStock(String minStock) {
        this.minStock.set(minStock);
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    //endregion
}
