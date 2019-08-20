package com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory;

import com.google.gson.annotations.SerializedName;

public class Replacement_Inventory {
    @SerializedName("id")
    private Integer id;
    @SerializedName("replacement_id")
    private Integer replacement_inv_replacement_id;
    @SerializedName("pen_id")
    private Integer replacement_inv_pen;
    @SerializedName("replacement_tag")
    private String replacement_inv_replacement_tag;
    @SerializedName("batching_date")
    private String replacement_inv_batching_date;
    @SerializedName("number_male")
    private Integer replacement_male_quantity;
    @SerializedName("number_female")
    private Integer replacement_female_quantity;
    @SerializedName("total")
    private Integer replacement_total_quantity;
    @SerializedName("last_update")
    private String replacement_inv_last_update;
    @SerializedName("deleted_at")
    private String replacement_inv_deleted_at;



    public Replacement_Inventory(){
    }
    public Replacement_Inventory(Integer id, Integer replacement_inv_replacement_id,Integer replacement_inv_pen, String replacement_inv_replacement_tag ,String replacement_inv_batching_date, Integer replacement_male_quantity, Integer replacement_female_quantity, Integer replacement_total_quantity,String replacement_inv_last_update, String replacement_inv_deleted_at) {
        this.setId(id);
        this.setReplacement_inv_replacement_id(replacement_inv_replacement_id);
        this.setReplacement_inv_pen(replacement_inv_pen);
        this.setReplacement_inv_replacement_tag(replacement_inv_replacement_tag);
        this.setReplacement_inv_batching_date(replacement_inv_batching_date);
        this.setReplacement_male_quantity(replacement_male_quantity);
        this.setReplacement_female_quantity(replacement_female_quantity);
        this.setReplacement_total_quantity(replacement_total_quantity);
        this.setReplacement_inv_last_update(replacement_inv_last_update);
        this.setReplacement_inv_deleted_at(replacement_inv_deleted_at);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReplacement_inv_replacement_id() {
        return replacement_inv_replacement_id;
    }

    public void setReplacement_inv_replacement_id(Integer replacement_inv_replacement_id) {
        this.replacement_inv_replacement_id = replacement_inv_replacement_id;
    }


    public Integer getReplacement_inv_pen() {
        return replacement_inv_pen;
    }

    public void setReplacement_inv_pen(Integer replacement_inv_pen) {
        this.replacement_inv_pen = replacement_inv_pen;
    }

    public String getReplacement_inv_replacement_tag() {
        return replacement_inv_replacement_tag;
    }

    public void setReplacement_inv_replacement_tag(String replacement_inv_replacement_tag) {
        this.replacement_inv_replacement_tag = replacement_inv_replacement_tag;
    }

    public String getReplacement_inv_batching_date() {
        return replacement_inv_batching_date;
    }

    public void setReplacement_inv_batching_date(String replacement_inv_batching_date) {
        this.replacement_inv_batching_date = replacement_inv_batching_date;
    }

    public Integer getReplacement_male_quantity() {
        return replacement_male_quantity;
    }

    public void setReplacement_male_quantity(Integer replacement_male_quantity) {
        this.replacement_male_quantity = replacement_male_quantity;
    }

    public Integer getReplacement_female_quantity() {
        return replacement_female_quantity;
    }

    public void setReplacement_female_quantity(Integer replacement_female_quantity) {
        this.replacement_female_quantity = replacement_female_quantity;
    }

    public Integer getReplacement_total_quantity() {
        return replacement_total_quantity;
    }

    public void setReplacement_total_quantity(Integer replacement_total_quantity) {
        this.replacement_total_quantity = replacement_total_quantity;
    }

    public String getReplacement_inv_last_update() {
        return replacement_inv_last_update;
    }

    public void setReplacement_inv_last_update(String replacement_inv_last_update) {
        this.replacement_inv_last_update = replacement_inv_last_update;
    }

    public String getReplacement_inv_deleted_at() {
        return replacement_inv_deleted_at;
    }

    public void setReplacement_inv_deleted_at(String replacement_inv_deleted_at) {
        this.replacement_inv_deleted_at = replacement_inv_deleted_at;
    }




}
