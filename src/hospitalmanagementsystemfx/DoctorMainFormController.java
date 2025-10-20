package hospitalmanagementsystemfx;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


public class DoctorMainFormController implements Initializable {

  @FXML
  private Button buttonAppointmentClear;

  @FXML
  private Button buttonAppointmentDelete;

  @FXML
  private Button buttonAppointmentInsert;

  @FXML
  private Button buttonAppointmentUpdate;

  @FXML
  private Button buttonAppointments;

  @FXML
  private Button buttonDashboard;

  @FXML
  private Button buttonImport;

  @FXML
  private Button buttonLogout;

  @FXML
  private Button buttonPatients;

  @FXML
  private Button buttonSettings;

  @FXML
  private Button buttonUpdateProfile;

  @FXML
  private BarChart<?, ?> chartDoctorData;

  @FXML
  private AreaChart<?, ?> chartPatientData;

  @FXML
  private Circle circleProfile;

  @FXML
  private TableColumn<?, ?> colAppointmentAction;

  @FXML
  private TableColumn<AppointmentData, String> colAppointmentContactNumber;

  @FXML
  private TableColumn<AppointmentData, String> colAppointmentDate;

  @FXML
  private TableColumn<AppointmentData, String> colAppointmentDateDelete;

  @FXML
  private TableColumn<AppointmentData, String> colAppointmentDateModify;

  @FXML
  private TableColumn<AppointmentData, String> colAppointmentDescription;

  @FXML
  private TableColumn<AppointmentData, String> colAppointmentGender;

  @FXML
  private TableColumn<AppointmentData, String> colAppointmentID;

  @FXML
  private TableColumn<AppointmentData, String> colAppointmentName;

  @FXML
  private TableColumn<AppointmentData, String> colAppointmentStatus;

  @FXML
  private TableColumn<?, ?> colDashboardDoctorId;

  @FXML
  private TableColumn<?, ?> colDashboardDoctorName;

  @FXML
  private TableColumn<?, ?> colDashboardSpecialty;

  @FXML
  private TableColumn<?, ?> colDashboardStatus;

  @FXML
  private ComboBox<String> comboAppointmentGender;

  @FXML
  private ComboBox<String> comboAppointmentStatus;

  @FXML
  private ComboBox<?> comboPatientGender;

  @FXML
  private DatePicker dateAppointmentSchedule;

  @FXML
  private AnchorPane formAppointments;

  @FXML
  private AnchorPane formDashboard;

  @FXML
  private AnchorPane formMain;

  @FXML
  private AnchorPane formPatients;

  @FXML
  private AnchorPane formProfile;

  @FXML
  private Label labelActiveDoctors;

  @FXML
  private Label labelActivePatients;

  @FXML
  private Label labelDoctorFullName;

  @FXML
  private Label labelDoctorId;

  @FXML
  private Label labelDoctorUsername;

  @FXML
  private Label labelCurrentForm;

  @FXML
  private Label labelDate;

  @FXML
  private Label labelDateCreated;

  @FXML
  private Label labelPatientAddress;

  @FXML
  private Label labelPatientDateCreated;

  @FXML
  private Label labelPatientGender;

  @FXML
  private Label labelPatientID;

  @FXML
  private Label labelPatientMobileNumber;

  @FXML
  private Label labelPatientName;

  @FXML
  private Label labelPatientPassword;

  @FXML
  private Label labelProfileAdminEmail;

  @FXML
  private Label labelProfileAdminId;

  @FXML
  private Label labelProfileAdminUsername;

  @FXML
  private Label labelTotalAppointments;

  @FXML
  private Label labelTotalPatients;

  @FXML
  private ComboBox<?> listGender;

  @FXML
  private TableView<AppointmentData> tableAppointments;

  @FXML
  private TableView<?> tableDoctorInformation;

  @FXML
  private TextField textAdminEmail;

  @FXML
  private TextField textAdminId;

  @FXML
  private TextField textAdminUsername;

  @FXML
  private TextArea textAppointmentAddress;

  @FXML
  private TextField textAppointmentDescription;

  @FXML
  private TextField textAppointmentDiagnosis;

  @FXML
  private TextField textAppointmentID;

  @FXML
  private TextField textAppointmentMobileNumber;

  @FXML
  private TextField textAppointmentName;

  @FXML
  private TextField textAppointmentTreatment;

  @FXML
  private TextArea textPatientAddress;

  @FXML
  private TextField textPatientId;

  @FXML
  private TextField textPatientMobileNumber;

  @FXML
  private TextField textPatientName;

  @FXML
  private TextField textPatientPassword;

  private Connection connect;
  private PreparedStatement prepare;
  private ResultSet result;
  private Statement statement;
  private AlertMessage alert = new AlertMessage();

  public ObservableList<AppointmentData> appointmentGetData() {
    ObservableList<AppointmentData> listData = FXCollections.observableArrayList();

    String sql = "SELECT * FROM Appointment WHERE DeleteDate IS NULL";

    connect = Database.connectDB();

    try {
      prepare = connect.prepareStatement(sql);
      result = prepare.executeQuery();
      AppointmentData apptData;

      while (result.next()) {
        // AppointmentData(int appointmentId, String name, String gender, String mobileNumber, String description, String diagnosis, String treament
        //        , java.sql.Date date, java.sql.Date dateModify, java.sql.Date dateDelete, String status, Date schedule)
        apptData = new AppointmentData(result.getInt("AppointmentId")
                , result.getString("Name")
                , result.getString("Gender")
                , result.getString("MobileNumber")
                , result.getString("Description")
                , result.getString("Diagnosis")
                , result.getString("Treatment")
                , result.getString("Address")
                , result.getDate("Date")
                , result.getDate("ModifyDate")
                , result.getDate("DeleteDate")
                , result.getString("Status")
                , result.getDate("Schedule"));

        // store all data
        listData.add(apptData);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return listData;
  }

  public ObservableList<AppointmentData> appointmentListData;

  public void appointmentShowData() {
    appointmentListData = appointmentGetData();
    colAppointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
    colAppointmentName.setCellValueFactory(new PropertyValueFactory<>("name"));
    colAppointmentGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
    colAppointmentContactNumber.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
    colAppointmentDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    colAppointmentDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    colAppointmentDateModify.setCellValueFactory(new PropertyValueFactory<>("dateModify"));
    colAppointmentDateDelete.setCellValueFactory(new PropertyValueFactory<>("dateDelete"));
    colAppointmentStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

    tableAppointments.setItems(appointmentListData);
  }

  @FXML
  void buttonAppointmentInsertClick() {
    // check if the fields are empty
    if (textAppointmentID.getText().isEmpty()
            || textAppointmentName.getText().isEmpty()
            || comboAppointmentGender.getSelectionModel().getSelectedItem() == null
            || textAppointmentMobileNumber.getText().isEmpty()
            || textAppointmentDescription.getText().isEmpty()
            || textAppointmentAddress.getText().isEmpty()
            || comboAppointmentStatus.getSelectionModel().getSelectedItem() == null
            || dateAppointmentSchedule.getValue() == null) {
      alert.errorMessage("Please fill in all required fields");
    } else {
      String checkAppointmentId = "SELECT * FROM Appointment WHERE AppointmentID=" + textAppointmentID.getText();

      connect = Database.connectDB();

      try {
        statement = connect.createStatement();
        result = statement.executeQuery(checkAppointmentId);

        if (result.next()) {
          alert.errorMessage("Appointment ID " + textAppointmentID.getText() + " already exists");
        } else {
          String specialization = "";
          String getDoctorData = "SELECT * FROM Doctor WHERE DoctorId = '" + Data.doctorId + "'";
          statement = connect.createStatement();
          result = statement.executeQuery(getDoctorData);

          if (result.next()) {
            specialization = result.getString("Specialty");
          }

          String insertData = "INSERT INTO Appointment ";
          insertData += "(AppointmentId, Name, Gender, Description, Diagnosis, Treatment, MobileNumber, Address, Date, Status, Doctor, Specialty, Schedule) ";
          insertData += "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

          prepare = connect.prepareStatement(insertData);

          prepare.setString(1, textAppointmentID.getText());
          prepare.setString(2, textAppointmentName.getText());
          prepare.setString(3, (String) comboAppointmentGender.getSelectionModel().getSelectedItem());
          prepare.setString(4, textAppointmentDescription.getText());
          prepare.setString(5, textAppointmentDiagnosis.getText());
          prepare.setString(6, textAppointmentTreatment.getText());
          prepare.setString(7, textAppointmentMobileNumber.getText());
          prepare.setString(8, textAppointmentAddress.getText());

          java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());

          prepare.setString(9, "" + sqlDate);
          prepare.setString(10, (String) comboAppointmentStatus.getSelectionModel().getSelectedItem());
          prepare.setString(11, Data.doctorId);
          prepare.setString(12, specialization);
          prepare.setString(13, "" + dateAppointmentSchedule.getValue());

          prepare.executeUpdate();
          // refresh appointment table after insertion
          appointmentShowData();
          buttonAppointmentClearClick();
          showNextAppointmentId();
          alert.successMessage("Appointment added!");
        }

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  // clear all fields
  @FXML
  void buttonAppointmentClearClick() {
    textAppointmentID.clear();
    textAppointmentName.clear();
    comboAppointmentGender.getSelectionModel().clearSelection();
    textAppointmentMobileNumber.clear();
    textAppointmentDescription.clear();
    textAppointmentTreatment.clear();
    textAppointmentDiagnosis.clear();
    textAppointmentAddress.clear();
    comboAppointmentStatus.getSelectionModel().clearSelection();
    dateAppointmentSchedule.setValue(null);
    showNextAppointmentId();
  }

  @FXML
  void buttonAppointmentUpdateClick() {

    // check if the fields are empty
    if (textAppointmentID.getText().isEmpty()
            || textAppointmentName.getText().isEmpty()
            || comboAppointmentGender.getSelectionModel().getSelectedItem() == null
            || textAppointmentMobileNumber.getText().isEmpty()
            || textAppointmentDescription.getText().isEmpty()
            || textAppointmentAddress.getText().isEmpty()
            || comboAppointmentStatus.getSelectionModel().getSelectedItem() == null
            || dateAppointmentSchedule.getValue() == null) {
      alert.errorMessage("Please fill in all required fields");
    } else {
      // get current date
      java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());

      String updateData = "UPDATE Appointment SET ";
      updateData += "Name='" + textAppointmentName.getText() + "', ";
      updateData += "Gender='" + comboAppointmentGender.getSelectionModel().getSelectedItem() + "', ";
      updateData += "MobileNumber='" + textAppointmentMobileNumber.getText() + "', ";
      updateData += "Description='" + textAppointmentDescription.getText() + "', ";
      updateData += "Address='" + textAppointmentAddress.getText() + "', ";
      updateData += "Status='" + comboAppointmentStatus.getSelectionModel().getSelectedItem() + "', ";
      updateData += "Schedule='" + dateAppointmentSchedule.getValue() + "', ";
      updateData += "ModifyDate='" + sqlDate + "' ";
      updateData += " WHERE AppointmentId='" + textAppointmentID.getText() + "'";

      connect = Database.connectDB();

      try {
        if (alert.confirmMessage("Update this appointment: " + textAppointmentID.getText() + "?")) {
          prepare = connect.prepareStatement(updateData);
          prepare.executeUpdate();

          appointmentShowData();
          buttonAppointmentClearClick();
          showNextAppointmentId();
          alert.successMessage("Appointment updated!");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @FXML
  void buttonAppointmentDeleteClick() {
    if (textAppointmentID.getText().isEmpty()) {
      alert.errorMessage("Please select an appointment to delete");
    } else {
      String updateData = "UPDATE Appointment SET DeleteDate=? WHERE AppointmentId='" + textAppointmentID.getText() + "' ";

      connect = Database.connectDB();

      try {
        if (alert.confirmMessage("Delete this appointment: " + textAppointmentID.getText() + "?")) {
          java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());

          prepare = connect.prepareStatement(updateData);
          prepare.setString(1, String.valueOf(sqlDate));
          prepare.executeUpdate();

          appointmentShowData();
          buttonAppointmentClearClick();
          showNextAppointmentId();

          alert.successMessage("Appointment deleted!");
        } else {
          alert.errorMessage("Delete canceled");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }


  private int appointmentId;

  public void getNextAppointmentId() {
    String sql = "SELECT MAX(AppointmentId) FROM Appointment";
    connect = Database.connectDB();

    int appointmentIdTemp = 0;

    try {
      prepare = connect.prepareStatement(sql);
      result = prepare.executeQuery();

      if (result.next()) {
        appointmentIdTemp = result.getInt("Max(AppointmentId)");
      }
      appointmentIdTemp++;
      appointmentId = appointmentIdTemp;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void showNextAppointmentId() {
    getNextAppointmentId();
    textAppointmentID.setText("" + appointmentId);
    textAppointmentID.setDisable(true);
  }

  // get info for selected appointment in table
  public void tableAppointmentSelect() {
    AppointmentData apptData = tableAppointments.getSelectionModel().getSelectedItem();
    int num = tableAppointments.getSelectionModel().getSelectedIndex();

    if ((num - 1) < -1) return;

    textAppointmentID.setText("" + apptData.getAppointmentId());
    textAppointmentName.setText(apptData.getName());
    comboAppointmentGender.getSelectionModel().select(apptData.getGender());
    textAppointmentMobileNumber.setText("" + apptData.getMobileNumber());
    textAppointmentDescription.setText("" + apptData.getDescription());
    textAppointmentDiagnosis.setText("" + apptData.getDiagnosis());
    textAppointmentTreatment.setText("" + apptData.getTreatment());
    textAppointmentAddress.setText("" + apptData.getAddress());
    comboAppointmentStatus.getSelectionModel().select(apptData.getStatus());
    //dateAppointmentSchedule.setValue(apptData.getSchedule());
  }

  @FXML
  void patientConfirmBtn(ActionEvent event) {

  }

  public void appointmentGenderList() {
    List<String> listGender = new ArrayList<>();

    for (String data : Data.gender) {
      listGender.add(data);
    }

    ObservableList listData = FXCollections.observableArrayList(listGender);
    comboAppointmentGender.setItems(listData);
  }

  public void appointmentStatusList() {
    List<String> listStatus = new ArrayList<>();

    for (String data : Data.status) {
      listStatus.add(data);
    }

    ObservableList listData = FXCollections.observableArrayList(listStatus);
    comboAppointmentStatus.setItems(listData);
  }

  public void displayDoctorInfo() {
    String doctorName = Data.doctorName;
    String doctorId = Data.doctorId;

    labelDoctorId.setText(doctorId);
    labelDoctorFullName.setText(doctorName);
    labelDoctorUsername.setText(doctorName);
  }

  @FXML
  public void switchForm(ActionEvent event) {
    hideForms();
    if (event.getSource() == buttonDashboard) {
      formDashboard.setVisible(true);
    }
    if (event.getSource() == buttonPatients) {
      formPatients.setVisible(true);
    }
    if (event.getSource() == buttonAppointments) {
      formAppointments.setVisible(true);
    }
    if (event.getSource() == buttonSettings) {
      formProfile.setVisible(true);
    }
  }

  private void hideForms() {
    formDashboard.setVisible(false);
    formPatients.setVisible(false);
    formAppointments.setVisible(false);
    formProfile.setVisible(false);
  }

  public void runTime() {
    new Thread() {
      public void run() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy hh:mm:ss a");
        while (true) {
          try {
            Thread.sleep(1000);
          } catch (Exception e) {
            e.printStackTrace();
          }

          Platform.runLater(() -> {
            labelDate.setText(format.format(new Date()));
          });
        }
      }
    }.start();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    hideForms();
    formDashboard.setVisible(true);
    displayDoctorInfo();
    runTime();
    // show appointment data when logging in
    appointmentShowData();
    appointmentGenderList();
    appointmentStatusList();
    showNextAppointmentId();
  }
}
