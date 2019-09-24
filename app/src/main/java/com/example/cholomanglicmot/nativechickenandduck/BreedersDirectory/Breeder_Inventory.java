package com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory;

import com.google.gson.annotations.SerializedName;

public class Breeder_Inventory {
    @SerializedName("id")
    private Integer id;
    @SerializedName("breeder_id")
    private Integer breeder_inv_breeder_id;
    @SerializedName("pen_id")
    private Integer breeder_inv_pen;
    @SerializedName("breeder_tag")
    private String breeder_inv_breeder_tag;
    @SerializedName("batching_date")
    private String breeder_inv_batching_date;
    @SerializedName("number_male")
    private Integer breeder_male_quantity;
    @SerializedName("number_female")
    private Integer breeder_female_quantity;
    @SerializedName("total")
    private Integer breeder_total_quantity;
    @SerializedName("last_update")
    private String breeder_inv_last_update;
    @SerializedName("deleted_at")
    private String breeder_inv_deleted_at;
    @SerializedName("breeder_code")
    private String breeder_code;
    @SerializedName("male_wingband")
    private String male_wingband;
    @SerializedName("female_wingband")
    private String female_wingband;

    public Breeder_Inventory(){
    }

    public Breeder_Inventory(Integer id, Integer breeder_inv_breeder_id, Integer breeder_inv_pen, String breeder_inv_breeder_tag, String breeder_inv_batching_date, Integer breeder_male_quantity, Integer breeder_female_quantity, Integer breeder_total_quantity, String breeder_inv_last_update, String breeder_inv_deleted_at, String breeder_code, String male_wingband, String female_wingband) {
        this.setId(id);
        this.setbreeder_inv_breeder_id(breeder_inv_breeder_id);
        this.setbreeder_inv_pen(breeder_inv_pen);
        this.setbreeder_inv_breeder_tag(breeder_inv_breeder_tag);
        this.setbreeder_inv_batching_date(breeder_inv_batching_date);
        this.setbreeder_male_quantity(breeder_male_quantity);
        this.setbreeder_female_quantity(breeder_female_quantity);
        this.setbreeder_total_quantity(breeder_total_quantity);
        this.setbreeder_inv_last_update(breeder_inv_last_update);
        this.setbreeder_inv_deleted_at(breeder_inv_deleted_at);
        this.setBreeder_code(breeder_code);
        this.setMale_wingband(male_wingband);
        this.setFemale_wingband(female_wingband);



    }

    public String getBreeder_code() {
        return breeder_code;
    }

    private void setBreeder_code(String breeder_code) {
        this.breeder_code = breeder_code;
    }

    public String getMale_wingband() {
        return male_wingband;
    }

    private void setMale_wingband(String male_wingband) {
        this.male_wingband = male_wingband;
    }

    public String getFemale_wingband() {
        return female_wingband;
    }

    private void setFemale_wingband(String female_wingband) {
        this.female_wingband = female_wingband;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getBreeder_inv_breeder_id() {
        return breeder_inv_breeder_id;
    }

    private void setbreeder_inv_breeder_id(Integer breeder_inv_breeder_id) {
        this.breeder_inv_breeder_id = breeder_inv_breeder_id;
    }

    public Integer getBreeder_inv_pen() {
        return breeder_inv_pen;
    }

    private void setbreeder_inv_pen(Integer breeder_inv_pen) {
        this.breeder_inv_pen = breeder_inv_pen;
    }

    public String getBreeder_inv_breeder_tag() {
        return breeder_inv_breeder_tag;
    }

    private void setbreeder_inv_breeder_tag(String breeder_inv_breeder_tag) {
        this.breeder_inv_breeder_tag = breeder_inv_breeder_tag;
    }

    public String getBreeder_inv_batching_date() {
        return breeder_inv_batching_date;
    }

    private void setbreeder_inv_batching_date(String breeder_inv_batching_date) {
        this.breeder_inv_batching_date = breeder_inv_batching_date;
    }

    public Integer getBreeder_male_quantity() {
        return breeder_male_quantity;
    }

    private void setbreeder_male_quantity(Integer breeder_male_quantity) {
        this.breeder_male_quantity = breeder_male_quantity;
    }

    public Integer getBreeder_female_quantity() {
        return breeder_female_quantity;
    }

    private void setbreeder_female_quantity(Integer breeder_female_quantity) {
        this.breeder_female_quantity = breeder_female_quantity;
    }

    public Integer getBreeder_total_quantity() {
        return breeder_total_quantity;
    }

    private void setbreeder_total_quantity(Integer breeder_total_quantity) {
        this.breeder_total_quantity = breeder_total_quantity;
    }

    public String getBreeder_inv_last_update() {
        return breeder_inv_last_update;
    }

    private void setbreeder_inv_last_update(String breeder_inv_last_update) {
        this.breeder_inv_last_update = breeder_inv_last_update;
    }

    public String getBreeder_inv_deleted_at() {
        return breeder_inv_deleted_at;
    }

    private void setbreeder_inv_deleted_at(String breeder_inv_deleted_at) {
        this.breeder_inv_deleted_at = breeder_inv_deleted_at;
    }
}
