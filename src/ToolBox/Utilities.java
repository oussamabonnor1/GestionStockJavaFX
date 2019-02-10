package ToolBox;

import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

public class Utilities {

    /*
    public static void rechercherTableauCommande(TableView table, String newValue, String ccp) {
        String condition = "";
        if (!ccp.matches("")) {
            condition = "and COMMANDE.CCP = '" + ccp + "'";
        }

        ObservableList<Commande> commandes, searchedList = FXCollections.observableArrayList();
        if (!newValue.matches("")) {
            commandes = (ObservableList<Commande>) DbConnection.selectMultipleElements("COMMANDE", condition);
            for (int i = 0; i < commandes.size(); i++) {
                if ((commandes.get(i)).getCode().contains(newValue)) {
                    Commande commande = commandes.get(i);
                    searchedList.add(commande);
                }
            }
            table.setItems(searchedList);
        } else {
            //DbConnection1.fillTableCommande(table, ccp);//TODO check
            System.out.println("rechercher tablea COMMANDE Utilities");
        }
    }*/

    public static boolean inputChecking(JFXTextField... fields) {
        boolean empty = true;
        for (JFXTextField field : fields) {
            if (field.getText().matches("")) empty = false;
        }
        return empty;
    }

    public static void warningPannel(String title, String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        alert.show();
    }

    public static boolean confirmationPanel(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("launchers/res/img/info.png"));
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public static void inputDeleting(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.setText("");
        }
    }

    public static void settingDates(TextField... textFields) {
        LocalDateTime ldt = LocalDateTime.now();
        String s = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", Locale.ENGLISH).format(ldt);
        for (TextField textField : textFields) {
            textField.setText(s);
        }
    }

    public static String getTodayDate(String format) {
        LocalDateTime ldt = LocalDateTime.now();
        return DateTimeFormatter.ofPattern(format, Locale.ENGLISH).format(ldt);
    }

    public static String getDateFromTextFields(TextField day, TextField month, TextField year) {
        if (day.getText().matches("") || month.getText().matches("") || year.getText().matches("")) {
            return "";
        } else {
            String dayString = String.format("%02d", Integer.valueOf(day.getText()));
            String monnthString = String.format("%02d", Integer.valueOf(month.getText()));
            String yearString = String.format("%04d", Integer.valueOf(year.getText()));
            return dayString + "/" + monnthString + "/" + yearString;
        }
    }

    public static String[] getDayMonthYearFromDate(String preDate) {
        String[] date;
        date = preDate.split("/");
        return date;
    }

    public static void numericLimitedTextField(int maxLength, JFXTextField... textFields) {
        for (JFXTextField textField : textFields) {
            textField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue,
                                    String newValue) {
                    if (!newValue.matches("\\d*")) {
                        textField.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });
            textField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                    if (textField.getText().length() > maxLength) {
                        String s = textField.getText().substring(0, maxLength);
                        textField.setText(s);
                    }
                }
            });
        }
    }

    public static boolean checkIfCardExpierd(Date dateCNI) {
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(dateCNI);
        Calendar check = Calendar.getInstance();
        currentDate.add(Calendar.DATE, 3650);
        return currentDate.before(check);
    }

    public static String addTimeToDate(Date date, int nbrMonth) {
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(date);
        currentDate.add(Calendar.MONTH, nbrMonth);
        String day = String.format("%02d", currentDate.get(Calendar.DAY_OF_MONTH));
        String month = String.format("%02d", currentDate.get(Calendar.MONTH));
        String year = String.format("%02d", currentDate.get(Calendar.YEAR));
        return day + "/" + month + "/" + year;
    }

    public static int getMonthsBetweenDates(String dateFinal) {
        LocalDateTime ldt = LocalDateTime.now();
        String dateNow = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH).format(ldt);
        Period diff = Period.between(
                LocalDate.parse(dateFinal, DateTimeFormatter.ofPattern("dd/MM/yyyy")).withDayOfMonth(28),
                LocalDate.parse(dateNow, DateTimeFormatter.ofPattern("dd/MM/yyyy")).withDayOfMonth(28));
        return (int) diff.toTotalMonths();
    }

    public static Date getDateFromFields(TextField day, TextField month, TextField year) {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(day.getText() + "/" + month.getText() + "/" + year.getText());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static boolean checkIfDateValid(String date) {
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yy");
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
