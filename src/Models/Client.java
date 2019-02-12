package Models;

import javafx.beans.property.SimpleStringProperty;

public class Client {
    SimpleStringProperty nClient, name, adresse, telephone, fax;

    public Client(String nClient, String name, String adresse, String telephone, String fax) {
        this.nClient = new SimpleStringProperty(nClient);
        this.name = new SimpleStringProperty(name);
        this.adresse = new SimpleStringProperty(adresse);
        this.telephone = new SimpleStringProperty(telephone);
        this.fax = new SimpleStringProperty(fax);
    }

    //region Getters & Setters

    public String getnClient() {
        return nClient.get();
    }

    public SimpleStringProperty nClientProperty() {
        return nClient;
    }

    public void setnClient(String nClient) {
        this.nClient.set(nClient);
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
