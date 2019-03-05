package Models;

import javafx.beans.property.SimpleStringProperty;

public class ReceiptLivraison {

    SimpleStringProperty nBon, date, nFournisseur, nArticle, qntA;

    public ReceiptLivraison(String nBon, String date, String nFournisseur, String nArticle, String qntA) {
        this.nBon = new SimpleStringProperty(nBon);
        this.date = new SimpleStringProperty(date);
        this.nFournisseur = new SimpleStringProperty(nFournisseur);
        this.nArticle = new SimpleStringProperty(nArticle);
        this.qntA = new SimpleStringProperty(qntA);
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

    public String getnFournisseur() {
        return nFournisseur.get();
    }

    public SimpleStringProperty nFournisseurProperty() {
        return nFournisseur;
    }

    public void setnFournisseur(String nFournisseur) {
        this.nFournisseur.set(nFournisseur);
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

    public String getQntA() {
        return qntA.get();
    }

    public SimpleStringProperty qntAProperty() {
        return qntA;
    }

    public void setQntA(String qntA) {
        this.qntA.set(qntA);
    }

    //endregion
}
