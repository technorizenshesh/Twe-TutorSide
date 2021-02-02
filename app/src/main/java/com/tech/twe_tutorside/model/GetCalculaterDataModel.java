package com.tech.twe_tutorside.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCalculaterDataModel {

    @SerializedName("per_hour_payment")
    @Expose
    private String perHourPayment;
    @SerializedName("full_course_time")
    @Expose
    private String fullCourseTime;
    @SerializedName("hourly_details")
    @Expose
    private HourlyDetailsModel hourlyDetails;
    @SerializedName("weekly_details")
    @Expose
    private WeeklyDetailsModel weeklyDetails;
    @SerializedName("monthly_details")
    @Expose
    private MonthlyDetails monthlyDetails;
    @SerializedName("full_course")
    @Expose
    private FullCourseModel fullCourse;

    public String getPerHourPayment() {
        return perHourPayment;
    }

    public void setPerHourPayment(String perHourPayment) {
        this.perHourPayment = perHourPayment;
    }

    public String getFullCourseTime() {
        return fullCourseTime;
    }

    public void setFullCourseTime(String fullCourseTime) {
        this.fullCourseTime = fullCourseTime;
    }

    public HourlyDetailsModel getHourlyDetails() {
        return hourlyDetails;
    }

    public void setHourlyDetails(HourlyDetailsModel hourlyDetails) {
        this.hourlyDetails = hourlyDetails;
    }

    public WeeklyDetailsModel getWeeklyDetails() {
        return weeklyDetails;
    }

    public void setWeeklyDetails(WeeklyDetailsModel weeklyDetails) {
        this.weeklyDetails = weeklyDetails;
    }

    public MonthlyDetails getMonthlyDetails() {
        return monthlyDetails;
    }

    public void setMonthlyDetails(MonthlyDetails monthlyDetails) {
        this.monthlyDetails = monthlyDetails;
    }

    public FullCourseModel getFullCourse() {
        return fullCourse;
    }

    public void setFullCourse(FullCourseModel fullCourse) {
        this.fullCourse = fullCourse;
    }
}
