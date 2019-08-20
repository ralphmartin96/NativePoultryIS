package com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory;

import com.google.gson.annotations.SerializedName;

public class Brooder_FeedingRecords {
    @SerializedName("id")
    private Integer id;
    @SerializedName("broodergrower_inventory_id")
    private Integer brooder_feeding_inventory_id;
    @SerializedName("date_collected")
    private String brooder_feeding_date_collected;
   // @SerializedName("broodergrower_inventory_id")
    private String brooder_tag;
    @SerializedName("amount_offered")
    private Float brooder_feeding_offered;
    @SerializedName("amount_refused")
    private Float brooder_feeding_refused;
    @SerializedName("remarks")
    private String brooder_feeding_remarks;
    @SerializedName("deleted_at")
    private String brooder_feeding_deleted_at;

    public Brooder_FeedingRecords(){
    }

    public Brooder_FeedingRecords(Integer id,Integer brooder_feeding_inventory_id, String brooder_feeding_date_collected,String brooder_tag, Float brooder_feeding_offered, Float brooder_feeding_refused, String brooder_feeding_remarks, String brooder_feeding_deleted_at) {

        this.setId(id);
        this.setBrooder_feeding_inventory_id(brooder_feeding_inventory_id);
        this.setBrooder_feeding_date_collected(brooder_feeding_date_collected);
        this.setBrooder_tag(brooder_tag);
        this.setBrooder_feeding_offered(brooder_feeding_offered);
        this.setBrooder_feeding_refused(brooder_feeding_refused);
        this.setBrooder_feeding_remarks(brooder_feeding_remarks);
        this.setBrooder_feeding_deleted_at(brooder_feeding_deleted_at);
    }

    public String getBrooder_tag() {
        return brooder_tag;
    }

    public void setBrooder_tag(String brooder_tag) {
        this.brooder_tag = brooder_tag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBrooder_feeding_inventory_id() {
        return brooder_feeding_inventory_id;
    }

    public void setBrooder_feeding_inventory_id(Integer brooder_feeding_inventory_id) {
        this.brooder_feeding_inventory_id = brooder_feeding_inventory_id;
    }

    public String getBrooder_feeding_date_collected() {
        return brooder_feeding_date_collected;
    }

    public void setBrooder_feeding_date_collected(String brooder_feeding_date_collected) {
        this.brooder_feeding_date_collected = brooder_feeding_date_collected;
    }

    public Float getBrooder_feeding_offered() {
        return brooder_feeding_offered;
    }

    public void setBrooder_feeding_offered(Float brooder_feeding_offered) {
        this.brooder_feeding_offered = brooder_feeding_offered;
    }

    public Float getBrooder_feeding_refused() {
        return brooder_feeding_refused;
    }

    public void setBrooder_feeding_refused(Float brooder_feeding_refused) {
        this.brooder_feeding_refused = brooder_feeding_refused;
    }

    public String getBrooder_feeding_remarks() {
        return brooder_feeding_remarks;
    }

    public void setBrooder_feeding_remarks(String brooder_feeding_remarks) {
        this.brooder_feeding_remarks = brooder_feeding_remarks;
    }

    public String getBrooder_feeding_deleted_at() {
        return brooder_feeding_deleted_at;
    }

    public void setBrooder_feeding_deleted_at(String brooder_feeding_deleted_at) {
        this.brooder_feeding_deleted_at = brooder_feeding_deleted_at;
    }


}
