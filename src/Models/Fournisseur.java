package Models;

import javafx.beans.property.SimpleStringProperty;

public class Fournisseur {
    SimpleStringProperty nFournisseur, name, adresse, telephone, fax;

    public Fournisseur(String nFournisseur, String name, String adresse, String telephone, String fax) {
        this.nFournisseur = new SimpleStringProperty(nFournisseur);
        this.name = new SimpleStringProperty(name);
        this.adresse = new SimpleStringProperty(adresse);
        this.telephone = new SimpleStringProperty(telephone);
        this.fax = new SimpleStringProperty(fax);
    }

    //region Getters & Setters

    public String getnFournisseur() {
        return nFournisseur.get();
    }

    public SimpleStringProperty nFournisseurProperty() {
        return nFournisseur;
    }

    public void setnFournisseur(String nFournisseur) {
        this.nFournisseur.set(nFournisseur);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAdresse() {
        return adresse.get();
    }

    public SimpleStringProperty adresseProperty() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse.set(adresse);
    }

    public String getTelephone() {
        return telephone.get();
    }

    public SimpleStringProperty telephoneProperty() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }

    public String getFax() {
        return fax.get();
    }

    public SimpleStringProperty faxProperty() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax.set(fax);
    }

    //endregion
}
