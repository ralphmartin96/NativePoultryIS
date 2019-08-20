package com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory;

import com.google.gson.annotations.SerializedName;

public class Egg_Production {
    @SerializedName("id")
    private Integer id;
    @SerializedName("breeder_inventory_id")
    private Integer egg_breeder_inv_id;
    @SerializedName("date_collected")
    private String date;

    private String tag;
    @SerializedName("total_eggs_intact")
    private Integer intact;
    @SerializedName("total_egg_weight")
    private Float weight;

    private Float average_weight;
    @SerializedName("total_broken")
    private Integer broken;
    @SerializedName("total_rejects")
    private Integer rejects;
    @SerializedName("remarks")
    private String remarks;
    @SerializedName("deleted_at")
    private String deleted_at;
    @SerializedName("female_inventory")
    private String female_inventory;

    Egg_Production(){

    }
    Egg_Production(Integer id, Integer egg_breeder_inv_id, String date, String tag, Integer intact, Float weight,Float average_weight, Integer broken, Integer rejects, String remarks, String deleted_at, String female_inventory){
        this.setId(id);
        this.setEgg_breeder_inv_id(egg_breeder_inv_id);
        this.setDate(date);
        this.setTag(tag);
        this.setIntact(intact);
        this.setWeight(weight);
        this.setAverage_weight(average_weight);
        this.setBroken(broken);
        this.setRejects(rejects);
        this.setRemarks(remarks);
        this.setDeleted_at(deleted_at);
        this.setFemale_inventory(female_inventory);
    }

    public String getFemale_inventory() {
        return female_inventory;
    }

    public void setFemale_inventory(String female_inventory) {
        this.female_inventory = female_inventory;
    }

    public Float getAverage_weight() {
        return average_weight;
    }

    public void setAverage_weight(Float average_weight) {
        this.average_weight = average_weight;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEgg_breeder_inv_id() {
        return egg_breeder_inv_id;
    }

    public void setEgg_breeder_inv_id(Integer egg_breeder_inv_id) {
        this.egg_breeder_inv_id = egg_breeder_inv_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getIntact() {
        return intact;
    }

    public void setIntact(Integer intact) {
        this.intact = intact;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Integer getBroken() {
        return broken;
    }

    public void setBroken(Integer broken) {
        this.broken = broken;
    }

    public Integer getRejects() {
        return rejects;
    }

    public void setRejects(Integer rejects) {
        this.rejects = rejects;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }
}

