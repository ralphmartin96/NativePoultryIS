package com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory;

import com.google.gson.annotations.SerializedName;

public class Brooder_GrowthRecords {
    @SerializedName("id")
    private Integer id;
    @SerializedName("broodergrower_inventory_id")
    private Integer brooder_growth_inventory_id;
/*    @SerializedName("collection_day")*/
    private String brooder_growth_tag;

    @SerializedName("collection_day")
    private Integer brooder_growth_collection_day;
    @SerializedName("date_collected")
    private String brooder_growth_date_collected;
    @SerializedName("male_quantity")
    private Integer brooder_growth_male_quantity;
    @SerializedName("male_weight")
    private Float brooder_growth_male_weight;
    @SerializedName("female_quantity")
    private Integer brooder_growth_female_quantity;
    @SerializedName("female_weight")
    private Float brooder_growth_female_weight;
    @SerializedName("total_quantity")
    private Integer brooder_growth_total_quantity;
    @SerializedName("total_weight")
    private Float brooder_growth_total_weight;
    @SerializedName("deleted_atid")
    private String brooder_growth_deleted_at;

    Brooder_GrowthRecords(){

    }
    public Brooder_GrowthRecords(Integer id, Integer brooder_growth_inventory_id, String brooder_growth_tag, Integer brooder_growth_collection_day, String brooder_growth_date_collected, Integer brooder_growth_male_quantity, Float brooder_growth_male_weight, Integer brooder_growth_female_quantity, Float brooder_growth_female_weight, Integer brooder_growth_total_quantity, Float brooder_growth_total_weight, String brooder_growth_deleted_at){
        this.setId(id);
        this.setBrooder_growth_inventory_id(brooder_growth_inventory_id);
        this.setBrooder_growth_tag(brooder_growth_tag);
        this.setBrooder_growth_collection_day(brooder_growth_collection_day);
        this.setBrooder_growth_date_collected(brooder_growth_date_collected);
        this.setBrooder_growth_male_quantity(brooder_growth_male_quantity);
        this.setBrooder_growth_male_weight(brooder_growth_male_weight);
        this.setBrooder_growth_female_quantity(brooder_growth_female_quantity);
        this.setBrooder_growth_female_weight(brooder_growth_female_weight);
        this.setBrooder_growth_total_quantity(brooder_growth_total_quantity);
        this.setBrooder_growth_total_weight(brooder_growth_total_weight);
        this.setBrooder_growth_deleted_at(brooder_growth_deleted_at);
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBrooder_growth_inventory_id() {
        return brooder_growth_inventory_id;
    }

    public void setBrooder_growth_inventory_id(Integer brooder_growth_inventory_id) {
        this.brooder_growth_inventory_id = brooder_growth_inventory_id;
    }

    public Integer getBrooder_growth_collection_day() {
        return brooder_growth_collection_day;
    }

    public String getBrooder_growth_tag() {
        return brooder_growth_tag;
    }

    public void setBrooder_growth_tag(String brooder_growth_tag) {
        this.brooder_growth_tag = brooder_growth_tag;
    }



    public void setBrooder_growth_collection_day(Integer brooder_growth_collection_day) {
        this.brooder_growth_collection_day = brooder_growth_collection_day;
    }

    public String getBrooder_growth_date_collected() {
        return brooder_growth_date_collected;
    }

    public void setBrooder_growth_date_collected(String brooder_growth_date_collected) {
        this.brooder_growth_date_collected = brooder_growth_date_collected;
    }

    public Integer getBrooder_growth_male_quantity() {
        return brooder_growth_male_quantity;
    }

    public void setBrooder_growth_male_quantity(Integer brooder_growth_male_quantity) {
        this.brooder_growth_male_quantity = brooder_growth_male_quantity;
    }

    public Float getBrooder_growth_male_weight() {
        return brooder_growth_male_weight;
    }

    public void setBrooder_growth_male_weight(Float brooder_growth_male_weight) {
        this.brooder_growth_male_weight = brooder_growth_male_weight;
    }

    public Integer getBrooder_growth_female_quantity() {
        return brooder_growth_female_quantity;
    }

    public void setBrooder_growth_female_quantity(Integer brooder_growth_female_quantity) {
        this.brooder_growth_female_quantity = brooder_growth_female_quantity;
    }

    public Float getBrooder_growth_female_weight() {
        return brooder_growth_female_weight;
    }

    public void setBrooder_growth_female_weight(Float brooder_growth_female_weight) {
        this.brooder_growth_female_weight = brooder_growth_female_weight;
    }

    public Integer getBrooder_growth_total_quantity() {
        return brooder_growth_total_quantity;
    }

    public void setBrooder_growth_total_quantity(Integer brooder_growth_total_quantity) {
        this.brooder_growth_total_quantity = brooder_growth_total_quantity;
    }

    public Float getBrooder_growth_total_weight() {
        return brooder_growth_total_weight;
    }

    public void setBrooder_growth_total_weight(Float brooder_growth_total_weight) {
        this.brooder_growth_total_weight = brooder_growth_total_weight;
    }

    public String getBrooder_growth_deleted_at() {
        return brooder_growth_deleted_at;
    }

    public void setBrooder_growth_deleted_at(String brooder_growth_deleted_at) {
        this.brooder_growth_deleted_at = brooder_growth_deleted_at;
    }





}
