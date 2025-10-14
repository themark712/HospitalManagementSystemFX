package hospitalmanagementsystemfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class DoctorPageController implements Initializable {

  @FXML
  private Button buttonLogin;

  @FXML
  private Button buttonRegister;

  @FXML
  private CheckBox checkLoginShowPassword;

  @FXML
  private CheckBox checkRegisterShowPassword;

  @FXML
  private AnchorPane formLogin;

  @FXML
  private AnchorPane formMain;

  @FXML
  private AnchorPane formRegister;

  @FXML
  private Label label;

  @FXML
  private Label label1;

  @FXML
  private Label labelLogin;

  @FXML
  private Label labelRegister;

  @FXML
  private Hyperlink linkLoginHere;

  @FXML
  private Hyperlink linkRegisterHere;

  @FXML
  private PasswordField textLoginPassword;

  @FXML
  private TextField textLoginShowPassword;

  @FXML
  private TextField textLoginDoctorId;

  @FXML
  private TextField textRegisterFullName;

  @FXML
  private TextField textRegisterEmail;

  @FXML
  private PasswordField textRegisterPassword;

  @FXML
  private TextField textRegisterShowPassword;

  @FXML
  private TextField textRegisterDoctorId;

  @FXML
  private ComboBox<?> listUser;

  private Connection connect;
  private PreparedStatement prepare;
  private ResultSet result;

  private final AlertMessage alert = new AlertMessage();

  @FXML
  void loginAccount() {

    if (textLoginDoctorId.getText().isEmpty()
            || textLoginPassword.getText().isEmpty()) {
      alert.errorMessage("Incorrect doctor id/password");
    } else {
      String sql = "SELECT * FROM Doctor WHERE DoctorId=? AND Password=? AND DeleteDate IS NULL";
      connect = Database.connectDB();

      try {
        // check status of doctor logging in
        // If "Confirm", doctor account must be confirmed by admin
        String checkStatus = "SELECT Status FROM Doctor ";
        checkStatus += "WHERE DoctorId='" + textLoginDoctorId.getText() + "' AND Password='" + textLoginPassword.getText() + "' AND Status='Confirm'";

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
        prepare.setString(1, textLoginDoctorId.getText());
        prepare.setString(2, textLoginPassword.getText());
        result = prepare.executeQuery();

        if (result.next()) {
          if (result.getString("Status").equals("Confirm")) {
            alert.errorMessage("Need the confirmation of the Admin");
          } else {
            //prepare = connect.prepareStatement(sql);
            //prepare.setString(1, textLoginDoctorId.getText());
            //prepare.setString(2, textLoginPassword.getText());

            //result = prepare.executeQuery();

            //if (result.next()) {
            alert.successMessage("Login successful!");
            //}
          }
        } else {
          alert.errorMessage("Incorrect doctor id/password");
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

  @FXML
  void registerAccount() {

    if (textRegisterFullName.getText().isEmpty()
            || textRegisterEmail.getText().isEmpty()
            || textRegisterDoctorId.getText().isEmpty()
            || textRegisterPassword.getText().isEmpty()) {
      alert.errorMessage("Please fill all blank fields");
    } else {

      String checkDoctorID = "SELECT * FROM doctor WHERE doctor_id = '"
              + textRegisterDoctorId.getText() + "'";

      connect = Database.connectDB();

      try {

        if (!checkRegisterShowPassword.isVisible()) {
          if (!textRegisterShowPassword.getText().equals(textRegisterPassword.getText())) {
            textRegisterShowPassword.setText(textRegisterPassword.getText());
          }
        } else {
          if (!textRegisterShowPassword.getText().equals(textRegisterPassword.getText())) {
            textRegisterPassword.setText(textRegisterShowPassword.getText());
          }
        }

        prepare = connect.prepareStatement(checkDoctorID);
        result = prepare.executeQuery();

        if (result.next()) {
          alert.errorMessage(textRegisterDoctorId.getText() + " is already taken");
        } else if (textRegisterPassword.getText().length() < 8) {
          alert.errorMessage("Invalid password, at least 8 characters needed");
        } else {

          String insertData = "INSERT INTO doctor (full_name, email, doctor_id, password, date, status) "
                  + "VALUES(?,?,?,?,?,?)";

          prepare = connect.prepareStatement(insertData);

          Date date = new Date();
          java.sql.Date sqlDate = new java.sql.Date(date.getTime());

          prepare.setString(1, textRegisterFullName.getText());
          prepare.setString(2, textRegisterEmail.getText());
          prepare.setString(3, textRegisterDoctorId.getText());
          prepare.setString(4, textRegisterPassword.getText());
          prepare.setString(5, String.valueOf(sqlDate));
          prepare.setString(6, "Confirm");

          prepare.executeUpdate();

          alert.successMessage("Registration successful!");

        }

      } catch (Exception e) {
        e.printStackTrace();
      }

    }

  }

  @FXML
  void registerShowPassword() {

    if (checkRegisterShowPassword.isSelected()) {
      textRegisterShowPassword.setText(textRegisterPassword.getText());
      textRegisterShowPassword.setVisible(true);
      textRegisterPassword.setVisible(false);
    } else {
      textRegisterPassword.setText(textRegisterShowPassword.getText());
      textRegisterShowPassword.setVisible(false);
      textRegisterPassword.setVisible(true);
    }

  }

  public void userList() {
    List<String> users = new ArrayList<>();

    for (String user : Users.user) {
      users.add(user);
    }

    ObservableList listData = FXCollections.observableList(users);
    listUser.setItems(listData);
  }

  private void registerDoctorId() {
    // generates unique DoctorId field value based on current max Id in Doctor table,
    // prefixed with text "DID-"
    String doctorId = "DID-";
    int tempId = 0;
    String sql = "SELECT MAX(Id) FROM Doctor";

    connect = Database.connectDB();

    try {
      prepare = connect.prepareStatement(sql);
      result = prepare.executeQuery();

      if (result.next()) {
        tempId = result.getInt("MAX(Id)");
      }

      if (tempId == 0) {
        tempId += 1;
        doctorId += tempId;
      } else {
        doctorId += (tempId + 1);
      }

      textRegisterDoctorId.setText(doctorId);
      textRegisterDoctorId.setDisable(true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void switchPortal() {

    String portalSelection = (String) listUser.getSelectionModel().getSelectedItem();
    Utlities.switchThePortal(listUser, portalSelection);

    /*
    if (listUser.getSelectionModel().getSelectedItem() == "Admin Portal") {
      try {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Stage stage = new Stage();

        stage.setTitle("Hospital Management System");
        stage.setMinWidth(330);
        stage.setMinHeight(550);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();

      } catch (Exception e) {
        e.printStackTrace();
      }
    } else if (listUser.getSelectionModel().getSelectedItem() == "Doctor Portal") {
      try {
        Parent root = FXMLLoader.load(getClass().getResource("DoctorPage.fxml"));
        Stage stage = new Stage();

        stage.setTitle("Hospital Management System");
        stage.setMinWidth(330);
        stage.setMinHeight(550);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();

      } catch (Exception e) {
        e.printStackTrace();
      }
    } else if (listUser.getSelectionModel().getSelectedItem() == "Patient Portal") {
      try {
        Parent root = FXMLLoader.load(getClass().getResource("PatientPage.fxml"));
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
    */
  }

  @FXML
  void switchForm(ActionEvent event) {
    if (event.getSource() == linkLoginHere) {
      formRegister.setVisible(false);
      formLogin.setVisible(true);
    } else if (event.getSource() == linkRegisterHere) {
      formLogin.setVisible(false);
      formRegister.setVisible(true);
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    userList();
    registerDoctorId();
  }
}
