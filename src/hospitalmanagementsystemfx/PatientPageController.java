package hospitalmanagementsystemfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PatientPageController implements Initializable {

  @FXML
  private Button buttonLogin;

  @FXML
  private CheckBox checkLoginShowPassword;

  @FXML
  private AnchorPane formLogin;

  @FXML
  private AnchorPane formMain;

  @FXML
  private Label label1;

  @FXML
  private Label labelLogin;

  @FXML
  private Hyperlink linkRegisterHere;

  @FXML
  private ComboBox<?> listUser;

  @FXML
  private PasswordField textLoginPassword;

  @FXML
  private TextField textLoginPatientId;

  @FXML
  private TextField textLoginShowPassword;

  private Connection connect;
  private PreparedStatement prepare;
  private ResultSet result;

  private final AlertMessage alert = new AlertMessage();

  @FXML
  void loginAccount() {

    if (textLoginPatientId.getText().isEmpty()
            || textLoginPassword.getText().isEmpty()) {
      alert.errorMessage("Incorrect patient id/password");
    } else {
      String sql = "SELECT * FROM Patient WHERE PatientId=? AND Password=? AND DeleteDate IS NULL";
      connect = Database.connectDB();

      try {
        // check status of patient logging in
        // If "Confirm", patient account must be confirmed by admin
        String checkStatus = "SELECT Status FROM Patient ";
        checkStatus += "WHERE PatientId='" + textLoginPatientId.getText() + "' AND Password='" + textLoginPassword.getText() + "' AND Status='Confirm'";

        //prepare = connect.prepareStatement(checkStatus);
        //result  = prepare.executeQuery();

        // if user switches between show password field, update the text fields so the passwords match
        if (!textLoginShowPassword.isVisible()) {
          if (!textLoginShowPassword.getText().equals(textLoginPassword.getText())) {
            textLoginShowPassword.setText(textLoginPassword.getText());
          }
        } else {
          if (!textLoginShowPassword.getText().equals(textLoginPassword.getText())) {
            textLoginPassword.setText(textLoginShowPassword.getText());
          }
        }

        prepare = connect.prepareStatement(sql);
        prepare.setString(1, textLoginPatientId.getText());
        prepare.setString(2, textLoginPassword.getText());
        result = prepare.executeQuery();

        if (result.next()) {
          if (result.getString("Status").equals("Confirm")) {
            alert.errorMessage("Need the confirmation of the Admin");
          } else {
            //prepare = connect.prepareStatement(sql);
            //prepare.setString(1, textLoginPatientId.getText());
            //prepare.setString(2, textLoginPassword.getText());

            //result = prepare.executeQuery();

            //if (result.next()) {
            alert.successMessage("Login successful!");
            //}
          }
        } else {
          alert.errorMessage("Incorrect patient id/password");
        }

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @FXML
  void loginShowPassword() {
    if (checkLoginShowPassword.isSelected()) {
      textLoginShowPassword.setText(textLoginPassword.getText());
      textLoginPassword.setVisible(false);
      textLoginShowPassword.setVisible(true);
    } else {
      textLoginPassword.setText(textLoginShowPassword.getText());
      textLoginShowPassword.setVisible(false);
      textLoginPassword.setVisible(true);
    }
  }

  public void switchPortal() {

    String portalSelection = (String) listUser.getSelectionModel().getSelectedItem();
    Utilities.switchThePortal(listUser, portalSelection);

  }

  public void userList() {
    List<String> users = new ArrayList<>();

    for (String user : Users.user) {
      users.add(user);
    }

    ObservableList listData = FXCollections.observableList(users);
    listUser.setItems(listData);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    userList();
  }

}
