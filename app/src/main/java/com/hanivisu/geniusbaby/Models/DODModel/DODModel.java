package com.hanivisu.geniusbaby.Models.DODModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DODModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("week")
    @Expose
    private Integer week;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}