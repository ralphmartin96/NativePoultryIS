package com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory;

import com.google.gson.annotations.SerializedName;

public class Brooders {
    @SerializedName("id")
    private Integer id;
    @SerializedName("family_id")
    private Integer brooder_family_number;
    @SerializedName("date_added")
    private String brooder_date_added;
    @SerializedName("deleted_at")
    private String brooder_deleted_at;


    public Brooders(){

    }

    public Brooders(Integer id, Integer brooder_family_number,  String brooder_date_added, String brooder_deleted_at){
        this.setId(id);
        this.setBrooder_family_number(brooder_family_number);
        this.setBrooder_date_added(brooder_date_added);
        this.setBrooder_deleted_at(brooder_deleted_at);

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getBrooder_family_number() {
        return brooder_family_number;
    }

    public void setBrooder_family_number(Integer brooder_family_number) {
        this.brooder_family_number = brooder_family_number;
    }

    public String getBrooder_date_added() {
        return brooder_date_added;
    }

    public void setBrooder_date_added(String brooder_date_added) {
        this.brooder_date_added = brooder_date_added;
    }

    public String getBrooder_deleted_at() {
        return brooder_deleted_at;
    }

    public void setBrooder_deleted_at(String brooder_deleted_at) {
        this.brooder_deleted_at = brooder_deleted_at;
    }

}
