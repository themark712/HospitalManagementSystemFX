/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package hospitalmanagementsystemfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

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
  private PasswordField textRegisterPasswod;

  @FXML
  private TextField textRegisterShowPassword;

  @FXML
  private TextField textRegisterUsername;

  public void switchForm(ActionEvent event) {
    if (event.getSource() == linkRegisterHere) {
      formLogin.setVisible(false);
      formRegister.setVisible(true);
    }
    else if (event.getSource() == linkLoginHere) {
      formRegister.setVisible(false);
      formLogin.setVisible(true);
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }

}
