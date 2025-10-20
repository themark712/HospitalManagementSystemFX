package hospitalmanagementsystemfx;

import java.sql.Date;

public class AppointmentData {
  private int appointmentId;
  private String patientId;
  private String name;
  private String gender;
  private String description;
  private String diagnosis;
  private String treatment;
  private String mobileNumber;
  private String address;
  private String address2;
  private Date date;
  private Date dateModify;
  private Date dateDelete;
  private String status;
  private String doctorId;
  private String specialty;
  private Date schedule;

  public AppointmentData(int appointmentId, String name, String gender, String mobileNumber, String description
          , String diagnosis, String treatment, String address, Date date, Date dateModify, Date dateDelete, String status, Date schedule) {
    this.appointmentId = appointmentId;
    this.name = name;
    this.gender = gender;
    this.mobileNumber = mobileNumber;
    this.diagnosis = diagnosis;
    this.treatment = treatment;
    this.address = address;
    this.description = description;
    this.date = date;
    this.dateModify = dateModify;
    this.dateDelete = dateDelete;
    this.status = status;
    this.schedule = schedule;
  }

  public int getAppointmentId() {
    return appointmentId;
  }

  public String getName() {
    return name;
  }

  public String getGender() {
    return gender;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public String getDescription() {
    return description;
  }

  public String getDiagnosis() {
    return diagnosis;
  }

  public String getTreatment() {
    return treatment;
  }

  public String getAddress() {
    return address;
  }

  public Date getDate() {
    return date;
  }

  public Date getDateModify() {
    return dateModify;
  }

  public Date getDateDelete() {
    return dateDelete;
  }

  public String getStatus() {
    return status;
  }

  public Date getSchedule() {
    return schedule;
  }
}
