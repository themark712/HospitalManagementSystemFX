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
        if (!textRegisterShowPassword.isVisible()) {
          if (!textRegisterShowPassword.getText().equals(textRegisterPassword.getText())) {
            textRegisterShowPassword.setText(textRegisterPassword.getText());
          }
        } else {
          if (!textRegisterShowPassword.getText().equals(textRegisterPassword.getText())) {
            textRegisterPassword.setText(textRegisterShowPassword.getText());
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
      alert.errorMessage("Please fill in all fields");
    } else {
      // check if doctor exists
      String checkDoctorId = "SELECT * FROM Doctor WHERE DoctorId='" + textRegisterDoctorId.getText() + "' ";
      checkDoctorId += " OR Email='" + textRegisterEmail.getText() + "'";

      connect = Database.connectDB();

      try {
        // if user switches between show password field, update the text fields so the passwords match
        if (!textRegisterShowPassword.isVisible()) {
          if (!textRegisterShowPassword.getText().equals(textRegisterPassword.getText())) {
            textRegisterShowPassword.setText(textRegisterPassword.getText());
          }
        } else {
          if (!textRegisterShowPassword.getText().equals(textRegisterPassword.getText())) {
            textRegisterPassword.setText(textRegisterShowPassword.getText());
          }
        }

        prepare = connect.prepareStatement(checkDoctorId);
        result = prepare.executeQuery();

        if (result.next()) {
          alert.errorMessage("Doctor username/email already exists");
          //} else if (textRegisterDoctorId.getText().length() < 8) {
          //  alert.errorMessage("Doctor ID be at least 8 characters");
        } else if (textRegisterPassword.getText().length() < 8) {
          alert.errorMessage("Password must be at least 8 characters");
        } else {
          // add new doctor to database
          String insertData = "INSERT INTO Doctor (FullName, DoctorId, Email, Password, Date, Status) VALUES (?, ?, ?, ?, ?, ?)";
          prepare = connect.prepareStatement(insertData);

          Date date = new Date();
          java.sql.Date sqlDate = new java.sql.Date(date.getDate());

          prepare.setString(1, textRegisterFullName.getText());
          prepare.setString(2, textRegisterDoctorId.getText());
          prepare.setString(3, textRegisterEmail.getText());
          prepare.setString(4, textRegisterPassword.getText());
          prepare.setString(5, String.valueOf(sqlDate));
          prepare.setString(6, "Confirm");

          prepare.executeUpdate();
          alert.successMessage("Doctor successfully registered!");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }


  public void registerClear() {
    textRegisterEmail.clear();
    textRegisterDoctorId.clear();
    textRegisterPassword.clear();
    textRegisterShowPassword.clear();
  }

  public void registerShowPassword() {
    if (checkRegisterShowPassword.isSelected()) {
      textRegisterShowPassword.setText(textRegisterPassword.getText());
      textRegisterPassword.setVisible(false);
      textRegisterShowPassword.setVisible(true);
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

    }

    listUser.getScene().getWindow().hide();
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
