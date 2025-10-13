package hospitalmanagementsystemfx;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
  private TextField textRegisterUsername;

  private Connection connection;
  private PreparedStatement preparedStatement;
  private ResultSet result;

  private final AlertMessage alert = new AlertMessage();

  @FXML
  void loginAccount(ActionEvent event) {

  }

  @FXML
  void loginShowPassword(ActionEvent event) {

  }

  @FXML
  void registerAccount(ActionEvent event) {

  }

  @FXML
  void registerShowPassword(ActionEvent event) {

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

  }
}
