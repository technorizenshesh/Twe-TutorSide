package com.tech.twe_tutorside.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRequestModelData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tutor_id")
    @Expose
    private String tutorId;
    @SerializedName("student_id")
    @Expose
    private String studentId;
    @SerializedName("reserve_type")
    @Expose
    private String reserveType;
    @SerializedName("monday")
    @Expose
    private String monday;
    @SerializedName("tuesday")
    @Expose
    private String tuesday;
    @SerializedName("wednesday")
    @Expose
    private String wednesday;
    @SerializedName("thursday")
    @Expose
    private String thursday;
    @SerializedName("friday")
    @Expose
    private String friday;
    @SerializedName("saturday")
    @Expose
    private String saturday;
    @SerializedName("sunday")
    @Expose
    private String sunday;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("startdate")
    @Expose
    private String startdate;
    @SerializedName("pakage_type")
    @Expose
    private String pakageType;
    @SerializedName("totalprice")
    @Expose
    private String totalprice;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("payement_status")
    @Expose
    private String payementStatus;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("date_time")
    @Expose
    private String dateTime;
    @SerializedName("student_details")
    @Expose
    private GetStudentDetailsModel studentDetails;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTutorId() {
        return tutorId;
    }

    public void setTutorId(String tutorId) {
        this.tutorId = tutorId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getReserveType() {
        return reserveType;
    }

    public void setReserveType(String reserveType) {
        this.reserveType = reserveType;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getPakageType() {
        return pakageType;
    }

    public void setPakageType(String pakageType) {
        this.pakageType = pakageType;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPayementStatus() {
        return payementStatus;
    }

    public void setPayementStatus(String payementStatus) {
        this.payementStatus = payementStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public GetStudentDetailsModel getStudentDetails() {
        return studentDetails;
    }

    public void setStudentDetails(GetStudentDetailsModel studentDetails) {
        this.studentDetails = studentDetails;
    }
}
