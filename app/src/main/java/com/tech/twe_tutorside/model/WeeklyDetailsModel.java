package com.tech.twe_tutorside.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeeklyDetailsModel {

    @SerializedName("service_charge")
    @Expose
    private Double serviceCharge;
    @SerializedName("discount_to_student")
    @Expose
    private Double discountToStudent;
    @SerializedName("teachers_earning")
    @Expose
    private Double teachersEarning;
    @SerializedName("student_price")
    @Expose
    private Double studentPrice;

    public Double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(Double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public Double getDiscountToStudent() {
        return discountToStudent;
    }

    public void setDiscountToStudent(Double discountToStudent) {
        this.discountToStudent = discountToStudent;
    }

    public Double getTeachersEarning() {
        return teachersEarning;
    }

    public void setTeachersEarning(Double teachersEarning) {
        this.teachersEarning = teachersEarning;
    }

    public Double getStudentPrice() {
        return studentPrice;
    }

    public void setStudentPrice(Double studentPrice) {
        this.studentPrice = studentPrice;
    }

}
