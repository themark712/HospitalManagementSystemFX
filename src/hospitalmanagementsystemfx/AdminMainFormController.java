package hospitalmanagementsystemfx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.event.ActionEvent;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class AdminMainFormController implements Initializable {

  @FXML
  private Button buttonAppointments;

  @FXML
  private Button buttonCheckout;

  @FXML
  private Button buttonDashboard;

  @FXML
  private Button buttonDoctors;

  @FXML
  private Button buttonImport;

  @FXML
  private Button buttonLogout;

  @FXML
  private Button buttonPatients;

  @FXML
  private Button buttonPayments;

  @FXML
  private Button buttonSettings;

  @FXML
  private Button buttonUpdateProfile;

  @FXML
  private BarChart<?, ?> chartDoctorData;

  @FXML
  private AreaChart<?, ?> chartPatientData;

  @FXML
  private Circle circlePayment;

  @FXML
  private Circle circleProfile;

  @FXML
  private TableColumn<?, ?> colAppointmentAction;

  @FXML
  private TableColumn<?, ?> colAppointmentContactNumber;

  @FXML
  private TableColumn<?, ?> colAppointmentDate;

  @FXML
  private TableColumn<?, ?> colAppointmentDateDelete;

  @FXML
  private TableColumn<?, ?> colAppointmentDateModify;

  @FXML
  private TableColumn<?, ?> colAppointmentDescription;

  @FXML
  private TableColumn<?, ?> colAppointmentGender;

  @FXML
  private TableColumn<?, ?> colAppointmentID;

  @FXML
  private TableColumn<?, ?> colAppointmentName;

  @FXML
  private TableColumn<?, ?> colAppointmentStatus;

  @FXML
  private TableColumn<?, ?> colDashboardDoctorId;

  @FXML
  private TableColumn<?, ?> colDashboardDoctorName;

  @FXML
  private TableColumn<?, ?> colDashboardSpecialty;

  @FXML
  private TableColumn<?, ?> colDashboardStatus;

  @FXML
  private TableColumn<?, ?> colDoctorAction;

  @FXML
  private TableColumn<?, ?> colDoctorAddress;

  @FXML
  private TableColumn<?, ?> colDoctorContactNumber;

  @FXML
  private TableColumn<?, ?> colDoctorEmail;

  @FXML
  private TableColumn<?, ?> colDoctorGender;

  @FXML
  private TableColumn<?, ?> colDoctorId;

  @FXML
  private TableColumn<?, ?> colDoctorName;

  @FXML
  private TableColumn<?, ?> colDoctorSpecialization;

  @FXML
  private TableColumn<?, ?> colDoctorStatus;

  @FXML
  private TableColumn<?, ?> colPatientAction;

  @FXML
  private TableColumn<?, ?> colPatientContactNumber;

  @FXML
  private TableColumn<?, ?> colPatientDate;

  @FXML
  private TableColumn<?, ?> colPatientDateDelete;

  @FXML
  private TableColumn<?, ?> colPatientDescription;

  @FXML
  private TableColumn<?, ?> colPatientGender;

  @FXML
  private TableColumn<?, ?> colPatientID;

  @FXML
  private TableColumn<?, ?> colPatientName;

  @FXML
  private TableColumn<?, ?> colPatientStatus;

  @FXML
  private TableColumn<?, ?> colPatientateModify;

  @FXML
  private TableColumn<?, ?> colPaymentDate;

  @FXML
  private TableColumn<?, ?> colPaymentDiagnosis;

  @FXML
  private TableColumn<?, ?> colPaymentDoctor;

  @FXML
  private TableColumn<?, ?> colPaymentGender;

  @FXML
  private TableColumn<?, ?> colPaymentName;

  @FXML
  private TableColumn<?, ?> colPaymentPatientID;

  @FXML
  private AnchorPane formAppointments;

  @FXML
  private AnchorPane formDashboard;

  @FXML
  private AnchorPane formDoctors;

  @FXML
  private AnchorPane formMain;

  @FXML
  private AnchorPane formPatients;

  @FXML
  private AnchorPane formPayments;

  @FXML
  private AnchorPane formProfile;

  @FXML
  private Label labelActiveDoctors;

  @FXML
  private Label labelActivePatients;

  @FXML
  private Label labelAdminEmail;

  @FXML
  private Label labelAdminId;

  @FXML
  private Label labelAdminUsername;

  @FXML
  private Label labelCurrentForm;

  @FXML
  private Label labelDate;

  @FXML
  private Label labelDateCreated;

  @FXML
  private Label labelDoctor;

  @FXML
  private Label labelHeaderUsername;

  @FXML
  private Label labelPatientID;

  @FXML
  private Label labelPatientName;

  @FXML
  private Label labelPaymentDate;

  @FXML
  private Label labelTotalAppointments;

  @FXML
  private Label labelTotalPatients;

  @FXML
  private Label labelUsername;

  @FXML
  private Label lableAdminId;

  @FXML
  private ComboBox<?> listGender;

  @FXML
  private TableView<?> tableAppointments;

  @FXML
  private TableView<?> tableDoctorInformation;

  @FXML
  private TableView<?> tableDoctorsView;

  @FXML
  private TableView<?> tablePatients;

  @FXML
  private TextField textAdminEmail;

  @FXML
  private TextField textAdminId;

  @FXML
  private TextField textAdminUsername;

  @FXML
  public void switchForm(ActionEvent event) {
    hideForms();
    if (event.getSource() == buttonDashboard) {
      formDashboard.setVisible(true);
    }
    if (event.getSource() == buttonDoctors) {
      formDoctors.setVisible(true);
    }
    if (event.getSource() == buttonPatients) {
      formPatients.setVisible(true);
    }
    if (event.getSource() == buttonAppointments) {
      formAppointments.setVisible(true);
    }
    if (event.getSource() == buttonPayments) {
      formPayments.setVisible(true);
    }
    if (event.getSource() == buttonSettings) {
      formProfile.setVisible(true);
    }
  }

  private void hideForms() {
    formDashboard.setVisible(false);
    formDoctors.setVisible(false);
    formPatients.setVisible(false);
    formAppointments.setVisible(false);
    formPayments.setVisible(false);
    formProfile.setVisible(false);
  }

  public void runTime() {
    new Thread() {
      public void run() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyy hh:mm:ss a");
        while (true) {
          try {
            Thread.sleep(1000);
          } catch (Exception e) { e.printStackTrace(); }

          Platform.runLater(() -> {
            labelDate.setText(format.format(new Date()));
          });
        }
      }
    }.start();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    runTime();
  }
}
