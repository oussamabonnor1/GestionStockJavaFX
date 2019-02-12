package Models;

import javafx.beans.property.SimpleStringProperty;

public class Stock {
    SimpleStringProperty nArticle, date, qntA, qntL, stock;

    public Stock(String nArticle, String date, String qntL, String qntA, String stock) {
        this.date = new SimpleStringProperty(date);
        this.nArticle = new SimpleStringProperty(nArticle);
        this.qntA = new SimpleStringProperty(qntA);
        this.qntL = new SimpleStringProperty(qntL);
        this.stock= new SimpleStringProperty(stock);
    }

    //region Getters & Setters
    public String getdate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setdate(String date) {
        this.date.set(date);
    }

    public String getStock() {
        return stock.get();
    }

    public SimpleStringProperty stockProperty() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock.set(stock);
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

    public String getqntA() {
        return qntA.get();
    }

    public SimpleStringProperty qntAProperty() {
        return qntA;
    }

    public void setqntA(String qntA) {
        this.qntA.set(qntA);
    }

    public String getqntL() {
        return qntL.get();
    }

    public SimpleStringProperty qntLProperty() {
        return qntL;
    }

    public void setqntL(String qntL) {
        this.qntL.set(qntL);
    }

    //endregion

}
