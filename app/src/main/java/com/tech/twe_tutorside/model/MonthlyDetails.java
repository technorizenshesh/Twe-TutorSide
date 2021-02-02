package com.tech.twe_tutorside.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MonthlyDetails {

    @SerializedName("service_charge")
    @Expose
    private Integer serviceCharge;
    @SerializedName("discount_to_student")
    @Expose
    private Integer discountToStudent;
    @SerializedName("teachers_earning")
    @Expose
    private Integer teachersEarning;
    @SerializedName("student_price")
    @Expose
    private Integer studentPrice;

    public Integer getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(Integer serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public Integer getDiscountToStudent() {
        return discountToStudent;
    }

    public void setDiscountToStudent(Integer discountToStudent) {
        this.discountToStudent = discountToStudent;
    }

    public Integer getTeachersEarning() {
        return teachersEarning;
    }

    public void setTeachersEarning(Integer teachersEarning) {
        this.teachersEarning = teachersEarning;
    }

    public Integer getStudentPrice() {
        return studentPrice;
    }

    public void setStudentPrice(Integer studentPrice) {
        this.studentPrice = studentPrice;
    }
}
