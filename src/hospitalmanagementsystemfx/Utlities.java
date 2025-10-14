package hospitalmanagementsystemfx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class Utlities {

  public static void switchThePortal(ComboBox<?> listUser, String listSelection) {
    if (listSelection == "Admin Portal") {
      try {
        Parent root = FXMLLoader.load(Utlities.class.getResource("FXMLDocument.fxml"));
        Stage stage = new Stage();

        stage.setTitle("Hospital Management System");
        stage.setMinWidth(330);
        stage.setMinHeight(550);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();

      } catch(Exception e) {e.printStackTrace();}
    } else if (listSelection == "Doctor Portal") {
      try {
        Parent root = FXMLLoader.load(Utlities.class.getResource("DoctorPage.fxml"));
        Stage stage = new Stage();

        stage.setTitle("Hospital Management System");
        stage.setMinWidth(330);
        stage.setMinHeight(550);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();

      } catch(Exception e) {e.printStackTrace();}
    } else if (listSelection == "Patient Portal") {
      try {
        Parent root = FXMLLoader.load(Utlities.class.getResource("PatientPage.fxml"));
        Stage stage = new Stage();

        stage.setTitle("Hospital Management System");
        stage.setMinWidth(330);
        stage.setMinHeight(550);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();

      } catch(Exception e) {e.printStackTrace();}
    }

    listUser.getScene().getWindow().hide();
  }
}
