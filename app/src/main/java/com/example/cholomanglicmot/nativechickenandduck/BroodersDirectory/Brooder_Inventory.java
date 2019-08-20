package com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory;

import com.google.gson.annotations.SerializedName;

public class Brooder_Inventory {
    @SerializedName("id")
    private Integer id;
    @SerializedName("broodergrower_id")
    private Integer brooder_inv_brooder_id;
    @SerializedName("pen_id")
    private Integer brooder_inv_pen;
    @SerializedName("broodergrower_tag")
    private String brooder_inv_brooder_tag;
    @SerializedName("batching_date")
    private String brooder_inv_batching_date;
    @SerializedName("number_male")
    private Integer brooder_male_quantity;
    @SerializedName("number_female")
    private Integer brooder_female_quantity;
    @SerializedName("total")
    private Integer brooder_total_quantity;
    @SerializedName("last_update")
    private String brooder_inv_last_update;
    @SerializedName("deleted_at")
    private String brooder_inv_deleted_at;


    public Brooder_Inventory(){
    }

    public Brooder_Inventory(Integer id,Integer brooder_inv_brooder_id, Integer brooder_inv_pen, String brooder_inv_brooder_tag, String brooder_inv_batching_date, Integer brooder_male_quantity, Integer brooder_female_quantity, Integer brooder_total_quantity, String brooder_inv_last_update,String brooder_inv_deleted_at) {
        this.setId(id);
        this.setBrooder_inv_brooder_id(brooder_inv_brooder_id);
        this.setBrooder_inv_pen(brooder_inv_pen);
        this.setBrooder_inv_brooder_tag(brooder_inv_brooder_tag);
        this.setBrooder_inv_batching_date(brooder_inv_batching_date);
        this.setBrooder_male_quantity(brooder_male_quantity);
        this.setBrooder_female_quantity(brooder_female_quantity);
        this.setBrooder_total_quantity(brooder_total_quantity);
        this.setBrooder_inv_last_update(brooder_inv_last_update);
        this.setBrooder_inv_deleted_at(brooder_inv_deleted_at);


    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getBrooder_inv_brooder_id() {
        return brooder_inv_brooder_id;
    }

    public void setBrooder_inv_brooder_id(Integer brooder_inv_brooder_id) {
        this.brooder_inv_brooder_id = brooder_inv_brooder_id;
    }

    public Integer getBrooder_inv_pen() {
        return brooder_inv_pen;
    }

    public void setBrooder_inv_pen(Integer brooder_inv_pen) {
        this.brooder_inv_pen = brooder_inv_pen;
    }

    public String getBrooder_inv_brooder_tag() {
        return brooder_inv_brooder_tag;
    }

    public void setBrooder_inv_brooder_tag(String brooder_inv_brooder_tag) {
        this.brooder_inv_brooder_tag = brooder_inv_brooder_tag;
    }

    public String getBrooder_inv_batching_date() {
        return brooder_inv_batching_date;
    }

    public void setBrooder_inv_batching_date(String brooder_inv_batching_date) {
        this.brooder_inv_batching_date = brooder_inv_batching_date;
    }

    public Integer getBrooder_male_quantity() {
        return brooder_male_quantity;
    }

    public void setBrooder_male_quantity(Integer brooder_male_quantity) {
        this.brooder_male_quantity = brooder_male_quantity;
    }

    public Integer getBrooder_female_quantity() {
        return brooder_female_quantity;
    }

    public void setBrooder_female_quantity(Integer brooder_female_quantity) {
        this.brooder_female_quantity = brooder_female_quantity;
    }

    public Integer getBrooder_total_quantity() {
        return brooder_total_quantity;
    }

    public void setBrooder_total_quantity(Integer brooder_total_quantity) {
        this.brooder_total_quantity = brooder_total_quantity;
    }

    public String getBrooder_inv_last_update() {
        return brooder_inv_last_update;
    }

    public void setBrooder_inv_last_update(String brooder_inv_last_update) {
        this.brooder_inv_last_update = brooder_inv_last_update;
    }

    public String getBrooder_inv_deleted_at() {
        return brooder_inv_deleted_at;
    }

    public void setBrooder_inv_deleted_at(String brooder_inv_deleted_at) {
        this.brooder_inv_deleted_at = brooder_inv_deleted_at;
    }
}
