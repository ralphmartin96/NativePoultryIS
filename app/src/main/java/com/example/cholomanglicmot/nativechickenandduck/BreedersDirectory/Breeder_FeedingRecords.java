package com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory;

import com.google.gson.annotations.SerializedName;

public class Breeder_FeedingRecords {
    @SerializedName("id")
    private Integer id;
    @SerializedName("breeder_inventory_id")
    private Integer breeder_feeding_inventory_id;
    @SerializedName("date_collected")
    private String breeder_feeding_date_collected;
    private String breeder_feeding_tag;
    @SerializedName("amount_offered")
    private Float breeder_feeding_offered;
    @SerializedName("amount_refused")
    private Float breeder_feeding_refused;
    @SerializedName("remarks")
    private String breeder_feeding_remarks;
    @SerializedName("deleted_at")
    private String breeder_feeding_deleted_at;


    public Breeder_FeedingRecords(){
    }

    public Breeder_FeedingRecords(Integer id, Integer breeder_feeding_inventory_id, String breeder_feeding_date_collected, String breeder_feeding_tag,Float breeder_feeding_offered, Float breeder_feeding_refused, String breeder_feeding_remarks, String breeder_feeding_deleted_at) {

        this.setId(id);
        this.setBreeder_feeding_inventory_id(breeder_feeding_inventory_id);
        this.setBreeder_feeding_date_collected(breeder_feeding_date_collected);
        this.setBreeder_feeding_tag(breeder_feeding_tag);
        this.setBreeder_feeding_offered(breeder_feeding_offered);
        this.setBreeder_feeding_refused(breeder_feeding_refused);
        this.setBreeder_feeding_remarks(breeder_feeding_remarks);
        this.setBreeder_feeding_deleted_at(breeder_feeding_deleted_at);
    }


    public String getBreeder_feeding_tag() {
        return breeder_feeding_tag;
    }

    public void setBreeder_feeding_tag(String breeder_feeding_tag) {
        this.breeder_feeding_tag = breeder_feeding_tag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBreeder_feeding_inventory_id() {
        return breeder_feeding_inventory_id;
    }

    public void setBreeder_feeding_inventory_id(Integer breeder_feeding_inventory_id) {
        this.breeder_feeding_inventory_id = breeder_feeding_inventory_id;
    }

    public String getBreeder_feeding_date_collected() {
        return breeder_feeding_date_collected;
    }

    public void setBreeder_feeding_date_collected(String breeder_feeding_date_collected) {
        this.breeder_feeding_date_collected = breeder_feeding_date_collected;
    }

    public Float getBreeder_feeding_offered() {
        return breeder_feeding_offered;
    }

    public void setBreeder_feeding_offered(Float breeder_feeding_offered) {
        this.breeder_feeding_offered = breeder_feeding_offered;
    }

    public Float getBreeder_feeding_refused() {
        return breeder_feeding_refused;
    }

    public void setBreeder_feeding_refused(Float breeder_feeding_refused) {
        this.breeder_feeding_refused = breeder_feeding_refused;
    }

    public String getBreeder_feeding_remarks() {
        return breeder_feeding_remarks;
    }

    public void setBreeder_feeding_remarks(String breeder_feeding_remarks) {
        this.breeder_feeding_remarks = breeder_feeding_remarks;
    }

    public String getBreeder_feeding_deleted_at() {
        return breeder_feeding_deleted_at;
    }

    public void setBreeder_feeding_deleted_at(String breeder_feeding_deleted_at) {
        this.breeder_feeding_deleted_at = breeder_feeding_deleted_at;
    }


}
