package Models;

import javafx.beans.property.SimpleStringProperty;

public class ReceiptLivraison {

    SimpleStringProperty nBon, date, nClient, nArticle, qntL;

    public ReceiptLivraison(String nBon, String date, String nClient, String nArticle, String qntL) {
        this.nBon = new SimpleStringProperty(nBon);
        this.date = new SimpleStringProperty(date);
        this.nClient = new SimpleStringProperty(nClient);
        this.nArticle = new SimpleStringProperty(nArticle);
        this.qntL = new SimpleStringProperty(qntL);
    }

    //region Setters & Getters
    public String getnBon() {
        return nBon.get();
    }

    public SimpleStringProperty nBonProperty() {
        return nBon;
    }

    public void setnBon(String nBon) {
        this.nBon.set(nBon);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getnClient() {
        return nClient.get();
    }

    public SimpleStringProperty nClientProperty() {
        return nClient;
    }

    public void setnClient(String nClient) {
        this.nClient.set(nClient);
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

    public String getQntL() {
        return qntL.get();
    }

    public SimpleStringProperty qntLProperty() {
        return qntL;
    }

    public void setQntL(String qntL) {
        this.qntL.set(qntL);
    }

    //endregion
}
