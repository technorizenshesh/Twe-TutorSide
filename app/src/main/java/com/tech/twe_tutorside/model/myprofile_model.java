package com.tech.twe_tutorside.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class myprofile_model {

    @SerializedName("result")
    @Expose
    private List<myprofile_data_model> result = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public List<myprofile_data_model> getResult() {
        return result;
    }

    public void setResult(List<myprofile_data_model> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
