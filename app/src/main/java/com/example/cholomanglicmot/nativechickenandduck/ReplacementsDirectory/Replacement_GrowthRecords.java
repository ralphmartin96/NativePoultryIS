package com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory;

import com.google.gson.annotations.SerializedName;

public class Replacement_GrowthRecords {
    @SerializedName("id")
    private Integer id;
    @SerializedName("replacement_inventory_id")
    private Integer replacement_growth_inventory_id;

    private String replacement_growth_tag;
    @SerializedName("collection_day")
    private Integer replacement_growth_collection_day;
    @SerializedName("date_collected")
    private String replacement_growth_date_collected;
    @SerializedName("male_quantity")
    private Integer replacement_growth_male_quantity;
    @SerializedName("male_weight")
    private Float replacement_growth_male_weight;
    @SerializedName("female_quantity")
    private Integer replacement_growth_female_quantity;
    @SerializedName("female_weight")
    private Float replacement_growth_female_weight;
    @SerializedName("total_quantity")
    private Integer replacement_growth_total_quantity;
    @SerializedName("total_weight")
    private Float replacement_growth_total_weight;
    @SerializedName("deleted_at")
    private String replacement_growth_deleted_at;

    Replacement_GrowthRecords(){

    }
    public Replacement_GrowthRecords(Integer id, Integer replacement_growth_inventory_id, String replacement_growth_tag, Integer replacement_growth_collection_day, String replacement_growth_date_collected, Integer replacement_growth_male_quantity, Float replacement_growth_male_weight, Integer replacement_growth_female_quantity, Float replacement_growth_female_weight, Integer replacement_growth_total_quantity, Float replacement_growth_total_weight, String replacement_growth_deleted_at){
        this.setId(id);
        this.setReplacement_growth_inventory_id(replacement_growth_inventory_id);
        this.setReplacement_growth_tag(replacement_growth_tag);
        this.setReplacement_growth_collection_day(replacement_growth_collection_day);
        this.setReplacement_growth_date_collected(replacement_growth_date_collected);
        this.setReplacement_growth_male_quantity(replacement_growth_male_quantity);
        this.setReplacement_growth_male_weight(replacement_growth_male_weight);
        this.setReplacement_growth_female_quantity(replacement_growth_female_quantity);
        this.setReplacement_growth_female_weight(replacement_growth_female_weight);
        this.setReplacement_growth_total_quantity(replacement_growth_total_quantity);
        this.setReplacement_growth_total_weight(replacement_growth_total_weight);
        this.setReplacement_growth_deleted_at(replacement_growth_deleted_at);
    }

    public Replacement_GrowthRecords(Integer id, Integer replacement_growth_inventory_id, Integer replacement_growth_collection_day, String replacement_growth_date_collected, Integer replacement_growth_male_quantity, Float replacement_growth_male_weight, Integer replacement_growth_female_quantity, Float replacement_growth_female_weight, Integer replacement_growth_total_quantity, Float replacement_growth_total_weight, String replacement_growth_deleted_at) {
        this.setId(id);
        this.setReplacement_growth_inventory_id(replacement_growth_inventory_id);
        this.setReplacement_growth_collection_day(replacement_growth_collection_day);
        this.setReplacement_growth_date_collected(replacement_growth_date_collected);
        this.setReplacement_growth_male_quantity(replacement_growth_male_quantity);
        this.setReplacement_growth_male_weight(replacement_growth_male_weight);
        this.setReplacement_growth_female_quantity(replacement_growth_female_quantity);
        this.setReplacement_growth_female_weight(replacement_growth_female_weight);
        this.setReplacement_growth_total_quantity(replacement_growth_total_quantity);
        this.setReplacement_growth_total_weight(replacement_growth_total_weight);
        this.setReplacement_growth_deleted_at(replacement_growth_deleted_at);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReplacement_growth_inventory_id() {
        return replacement_growth_inventory_id;
    }

    public void setReplacement_growth_inventory_id(Integer replacement_growth_inventory_id) {
        this.replacement_growth_inventory_id = replacement_growth_inventory_id;
    }

    public Integer getReplacement_growth_collection_day() {
        return replacement_growth_collection_day;
    }

    public String getReplacement_growth_tag() {
        return replacement_growth_tag;
    }

    public void setReplacement_growth_tag(String replacement_growth_tag) {
        this.replacement_growth_tag = replacement_growth_tag;
    }



    public void setReplacement_growth_collection_day(Integer replacement_growth_collection_day) {
        this.replacement_growth_collection_day = replacement_growth_collection_day;
    }

    public String getReplacement_growth_date_collected() {
        return replacement_growth_date_collected;
    }

    public void setReplacement_growth_date_collected(String replacement_growth_date_collected) {
        this.replacement_growth_date_collected = replacement_growth_date_collected;
    }

    public Integer getReplacement_growth_male_quantity() {
        return replacement_growth_male_quantity;
    }

    public void setReplacement_growth_male_quantity(Integer replacement_growth_male_quantity) {
        this.replacement_growth_male_quantity = replacement_growth_male_quantity;
    }

    public Float getReplacement_growth_male_weight() {
        return replacement_growth_male_weight;
    }

    public void setReplacement_growth_male_weight(Float replacement_growth_male_weight) {
        this.replacement_growth_male_weight = replacement_growth_male_weight;
    }

    public Integer getReplacement_growth_female_quantity() {
        return replacement_growth_female_quantity;
    }

    public void setReplacement_growth_female_quantity(Integer replacement_growth_female_quantity) {
        this.replacement_growth_female_quantity = replacement_growth_female_quantity;
    }

    public Float getReplacement_growth_female_weight() {
        return replacement_growth_female_weight;
    }

    public void setReplacement_growth_female_weight(Float replacement_growth_female_weight) {
        this.replacement_growth_female_weight = replacement_growth_female_weight;
    }

    public Integer getReplacement_growth_total_quantity() {
        return replacement_growth_total_quantity;
    }

    public void setReplacement_growth_total_quantity(Integer replacement_growth_total_quantity) {
        this.replacement_growth_total_quantity = replacement_growth_total_quantity;
    }

    public Float getReplacement_growth_total_weight() {
        return replacement_growth_total_weight;
    }

    public void setReplacement_growth_total_weight(Float replacement_growth_total_weight) {
        this.replacement_growth_total_weight = replacement_growth_total_weight;
    }

    public String getReplacement_growth_deleted_at() {
        return replacement_growth_deleted_at;
    }

    public void setReplacement_growth_deleted_at(String replacement_growth_deleted_at) {
        this.replacement_growth_deleted_at = replacement_growth_deleted_at;
    }





}

