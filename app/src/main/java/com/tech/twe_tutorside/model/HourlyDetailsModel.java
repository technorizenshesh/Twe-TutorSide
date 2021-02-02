package com.tech.twe_tutorside.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HourlyDetailsModel {

    @SerializedName("service_charge")
    @Expose
    private Integer serviceCharge;
    @SerializedName("discount_to_student")
    @Expose
    private String discountToStudent;
    @SerializedName("teachers_earning")
    @Expose
    private Integer teachersEarning;
    @SerializedName("student_price")
    @Expose
    private String studentPrice;

    public Integer getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(Integer serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getDiscountToStudent() {
        return discountToStudent;
    }

    public void setDiscountToStudent(String discountToStudent) {
        this.discountToStudent = discountToStudent;
    }

    public Integer getTeachersEarning() {
        return teachersEarning;
    }

    public void setTeachersEarning(Integer teachersEarning) {
        this.teachersEarning = teachersEarning;
    }

    public String getStudentPrice() {
        return studentPrice;
    }

    public void setStudentPrice(String studentPrice) {
        this.studentPrice = studentPrice;
    }

}
