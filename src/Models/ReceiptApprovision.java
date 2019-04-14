package Models;

import javafx.beans.property.SimpleStringProperty;

public class ReceiptApprovision {

    SimpleStringProperty nBon, date, nFournisseur, nArticle, qntA, label, prix;

    public ReceiptApprovision(String nBon, String date, String nFournisseur, String nArticle, String qntA, String label, String prix) {
        this.nBon = new SimpleStringProperty(nBon);
        this.date = new SimpleStringProperty(date);
        this.nFournisseur = new SimpleStringProperty(nFournisseur);
        this.nArticle = new SimpleStringProperty(nArticle);
        this.qntA = new SimpleStringProperty(qntA);
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
