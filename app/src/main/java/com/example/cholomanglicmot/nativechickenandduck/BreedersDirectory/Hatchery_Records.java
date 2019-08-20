package com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory;

import com.google.gson.annotations.SerializedName;

public class Hatchery_Records {
    @SerializedName("id")
    private Integer id;
    @SerializedName("breeder_inventory_id")
    private Integer breeder_inv_id;
    @SerializedName("date_eggs_set")
    private String date;
    private String tag;
    @SerializedName("batching_date")
    private String batching_date;
    @SerializedName("number_eggs_set")
    private Integer eggs_set;
    @SerializedName("week_of_lay")
    private Integer week_lay;
    @SerializedName("number_fertile")
    private Integer fertile;
    @SerializedName("number_hatched")
    private Integer hatched;
    @SerializedName("date_hatched")
    private String date_hatched;
    @SerializedName("deleted_at")
    private String deleted_at;

    Hatchery_Records(){

    }
    Hatchery_Records( Integer id, Integer breeder_inv_id,String date,String tag, String batching_date,Integer eggs_set, Integer week_lay, Integer fertile,  Integer hatched, String date_hatched, String deleted_at){
        this.setId(id);
        this.setBreeder_inv_id(breeder_inv_id);
        this.setDate(date);
        this.setTag(tag);
        this.setBatching_date(batching_date);
        this.setEggs_set(eggs_set);
        this.setWeek_lay(week_lay);
        this.setFertile(fertile);
        this.setHatched(hatched);
        this.setDate_hatched(date_hatched);
        this.setDeleted_at(deleted_at);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getBatching_date() {
        return batching_date;
    }

    public void setBatching_date(String batching_date) {
        this.batching_date = batching_date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBreeder_inv_id() {
        return breeder_inv_id;
    }

    public void setBreeder_inv_id(Integer breeder_inv_id) {
        this.breeder_inv_id = breeder_inv_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getEggs_set() {
        return eggs_set;
    }

    public void setEggs_set(Integer eggs_set) {
        this.eggs_set = eggs_set;
    }

    public Integer getWeek_lay() {
        return week_lay;
    }

    public void setWeek_lay(Integer week_lay) {
        this.week_lay = week_lay;
    }

    public Integer getFertile() {
        return fertile;
    }

    public void setFertile(Integer fertile) {
        this.fertile = fertile;
    }

    public Integer getHatched() {
        return hatched;
    }

    public void setHatched(Integer hatched) {
        this.hatched = hatched;
    }

    public String getDate_hatched() {
        return date_hatched;
    }

    public void setDate_hatched(String date_hatched) {
        this.date_hatched = date_hatched;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }
}
