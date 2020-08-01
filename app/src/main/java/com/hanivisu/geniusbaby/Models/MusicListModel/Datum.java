package com.hanivisu.geniusbaby.Models.MusicListModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Datum implements Serializable {

    @SerializedName("cid")
    @Expose
    private String cid;
    @SerializedName("cname")
    @Expose
    private String cname;
    @SerializedName("cdescription")
    @Expose
    private String cdescription;
    @SerializedName("cfile")
    @Expose
    private String cfile;
    @SerializedName("cday")
    @Expose
    private String cday;
    @SerializedName("cweek")
    @Expose
    private String cweek;
    @SerializedName("ctime")
    @Expose
    private String ctime;
    @SerializedName("cduration")
    @Expose
    private String cduration;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCdescription() {
        return cdescription;
    }

    public void setCdescription(String cdescription) {
        this.cdescription = cdescription;
    }

    public String getCfile() {
        return cfile;
    }

    public void setCfile(String cfile) {
        this.cfile = cfile;
    }

    public String getCday() {
        return cday;
    }

    public void setCday(String cday) {
        this.cday = cday;
    }

    public String getCweek() {
        return cweek;
    }

    @Override
    public String toString() {
        return "Datum{" +
                "cid='" + cid + '\'' +
                ", cname='" + cname + '\'' +
                ", cdescription='" + cdescription + '\'' +
                ", cfile='" + cfile + '\'' +
                ", cday='" + cday + '\'' +
                ", cweek='" + cweek + '\'' +
                ", ctime='" + ctime + '\'' +
                ", cduration='" + cduration + '\'' +
                '}';
    }

    public void setCweek(String cweek) {
        this.cweek = cweek;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getCduration() {
        return cduration;
    }

    public void setCduration(String cduration) {
        this.cduration = cduration;
    }

}