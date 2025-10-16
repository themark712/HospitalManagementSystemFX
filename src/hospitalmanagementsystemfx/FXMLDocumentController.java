/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package hospitalmanagementsystemfx;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.text.Utilities;

/**
 *
 * @author cni-mark
 */
public class FXMLDocumentController implements Initializable {

  // name all components on admin page
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
  private TextField textLoginUsername;

  @FXML
  private TextField textRegisterEmail;

  @FXML
  private PasswordField textRegisterPassword;

  @FXML
  private TextField textRegisterShowPassword;

  @FXML
  private TextField textRegisterUsername;

  @FXML
  private ComboBox<?> listUser;

  // database tools
  private Connection connect;
  private PreparedStatement prepare;
  private ResultSet result;

  private AlertMessage alert = new AlertMessage();

  public void loginAccount() {
    if (textLoginUsername.getText().isEmpty()
            || textLoginPassword.getText().isEmpty()) {
      alert.errorMessage("Incorrect username/password");
    } else {
      String sql = "SELECT * FROM Admin WHERE Username = ? AND password = ?";

      connect = Database.connectDB();

      try {
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
        prepare.setString(1, textLoginUsername.getText());
        prepare.setString(2, textLoginPassword.getText());
        result = prepare.executeQuery();

        if (result.next()) {
          // successful login
          alert.successMessage("Logged in successfully!");

          // link main form for admin
          Parent root = FXMLLoader.load(getClass().getResource("AdminMainForm.fxml"));
          Stage stage = new Stage();

          Image icon = new Image("hospital.png");
          stage.getIcons().add(icon);
          stage.setTitle("Hospital Management System | Admin Portal");
          stage.setScene(new Scene(root));

          stage.show();

          // hide login form
          buttonLogin.getScene().getWindow().hide();
        } else {
          // login failed
          alert.errorMessage("Incorrect username/password");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void loginShowPassword() {
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

  public void registerAccount() {
    if (textRegisterEmail.getText().isEmpty()
            || textRegisterUsername.getText().isEmpty()
            || textRegisterPassword.getText().isEmpty()) {
      // create alert message
      alert.errorMessage("Please complete all blank fields");
    } else {
      // check if username entered already exists
      String checkUsername = "SELECT * FROM Admin WHERE (Username = '" + textRegisterUsername.getText() + "' OR Email = '" + textRegisterEmail.getText() + "')";
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

        prepare = connect.prepareStatement(checkUsername);
        result = prepare.executeQuery();

        if (result.next()) {
          alert.errorMessage("This username and/or email address is already registered!");
        } else if (textRegisterUsername.getText().length() < 8) {
          alert.errorMessage("Username must be at least 8 characters!");
        } else if (textRegisterPassword.getText().length() < 8) {
          alert.errorMessage("Password must be at least 8 characters!");
        } else {
          // insert new user into Admin table
          String insertData = "INSERT INTO Admin (Email, Username, Password, Date) VALUES (?, ?, ?, ?)";

          Date date = new Date();
          java.sql.Date sqlDate = new java.sql.Date(date.getTime());

          prepare = connect.prepareStatement(insertData);
          prepare.setString(1, textRegisterEmail.getText());
          prepare.setString(2, textRegisterUsername.getText());
          prepare.setString(3, textRegisterPassword.getText());
          prepare.setString(4, String.valueOf(sqlDate));

          prepare.executeUpdate();

          alert.successMessage("Registered successfully!");
          registerClear();

          // after successful registration, switch to login form
          formRegister.setVisible(false);
          formLogin.setVisible(true);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void registerClear() {
    textRegisterEmail.clear();
    textRegisterUsername.clear();
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

  public void switchPortal() {
    String portalSelection = (String) listUser.getSelectionModel().getSelectedItem();
    Utlities.switchThePortal(listUser, portalSelection);
  }

  public void switchForm(ActionEvent event) {
    if (event.getSource() == linkRegisterHere) {
      formLogin.setVisible(false);
      formRegister.setVisible(true);
    } else if (event.getSource() == linkLoginHere) {
      formRegister.setVisible(false);
      formLogin.setVisible(true);
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    userList();
  }

}
