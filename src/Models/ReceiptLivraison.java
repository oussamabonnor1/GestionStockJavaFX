package Models;

import javafx.beans.property.SimpleStringProperty;

public class ReceiptLivraison {

    SimpleStringProperty nBon, date, nClient, nArticle, qntL, label, prix;

    public ReceiptLivraison(String nBon, String date, String nFournisseur, String nArticle, String qntA, String label, String prix) {
        this.nBon = new SimpleStringProperty(nBon);
        this.date = new SimpleStringProperty(date);
        this.nClient = new SimpleStringProperty(nFournisseur);
        this.nArticle = new SimpleStringProperty(nArticle);
        this.qntL = new SimpleStringProperty(qntA);
        this.label = new SimpleStringProperty(label);
        this.prix = new SimpleStringProperty(prix);
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

    public String getLabel() {
        return label.get();
    }

    public SimpleStringProperty labelProperty() {
        return label;
    }

    public void setLabel(String label) {
        this.label.set(label);
    }

    public String getPrix() {
        return prix.get();
    }

    public SimpleStringProperty prixProperty() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix.set(prix);
    }

    //endregion
}
