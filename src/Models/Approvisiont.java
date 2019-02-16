package Models;

import javafx.beans.property.SimpleStringProperty;

public class Approvisiont {
    SimpleStringProperty nBon, date, nFournisseur;

    public Approvisiont(String nBon, String date, String nFournisseur) {
        this.nBon = new SimpleStringProperty(nBon);
        this.date = new SimpleStringProperty(date);
        this.nFournisseur = new SimpleStringProperty(nFournisseur);
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
    //endregion
}
