package com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory;

import com.google.gson.annotations.SerializedName;

public class Replacement_FeedingRecords {
    @SerializedName("id")
    private Integer id;
    @SerializedName("replacement_inventory_id")
    private Integer replacement_feeding_inventory_id;
    @SerializedName("date_collected")
    private String replacement_feeding_date_collected;

    private String replacement_feeding_tag;
    @SerializedName("amount_offered")
    private Float replacement_feeding_offered;
    @SerializedName("amount_refused")
    private Float replacement_feeding_refused;
    @SerializedName("remarks")
    private String replacement_feeding_remarks;
    @SerializedName("deleted_at")
    private String replacement_feeding_deleted_at;

    public Replacement_FeedingRecords(){
    }

    public Replacement_FeedingRecords(Integer id, Integer replacement_feeding_inventory_id, String replacement_feeding_date_collected,String replacement_feeding_tag, Float replacement_feeding_offered, Float replacement_feeding_refused, String replacement_feeding_remarks, String replacement_feeding_deleted_at) {

        this.setId(id);
        this.setReplacement_feeding_inventory_id(replacement_feeding_inventory_id);
        this.setReplacement_feeding_date_collected(replacement_feeding_date_collected);
        this.setReplacement_feeding_tag(replacement_feeding_tag);
        this.setReplacement_feeding_offered(replacement_feeding_offered);
        this.setReplacement_feeding_refused(replacement_feeding_refused);
        this.setReplacement_feeding_remarks(replacement_feeding_remarks);
        this.setReplacement_feeding_deleted_at(replacement_feeding_deleted_at);
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReplacement_feeding_inventory_id() {
        return replacement_feeding_inventory_id;
    }

    public void setReplacement_feeding_inventory_id(Integer replacement_feeding_inventory_id) {
        this.replacement_feeding_inventory_id = replacement_feeding_inventory_id;
    }

    public String getReplacement_feeding_date_collected() {
        return replacement_feeding_date_collected;
    }

    public void setReplacement_feeding_date_collected(String replacement_feeding_date_collected) {
        this.replacement_feeding_date_collected = replacement_feeding_date_collected;
    }

    public String getReplacement_feeding_tag() {
        return replacement_feeding_tag;
    }

    public void setReplacement_feeding_tag(String replacement_feeding_tag) {
        this.replacement_feeding_tag = replacement_feeding_tag;
    }

    public Float getReplacement_feeding_offered() {
        return replacement_feeding_offered;
    }

    public void setReplacement_feeding_offered(Float replacement_feeding_offered) {
        this.replacement_feeding_offered = replacement_feeding_offered;
    }

    public Float getReplacement_feeding_refused() {
        return replacement_feeding_refused;
    }

    public void setReplacement_feeding_refused(Float replacement_feeding_refused) {
        this.replacement_feeding_refused = replacement_feeding_refused;
    }

    public String getReplacement_feeding_remarks() {
        return replacement_feeding_remarks;
    }

    public void setReplacement_feeding_remarks(String replacement_feeding_remarks) {
        this.replacement_feeding_remarks = replacement_feeding_remarks;
    }

    public String getReplacement_feeding_deleted_at() {
        return replacement_feeding_deleted_at;
    }

    public void setReplacement_feeding_deleted_at(String replacement_feeding_deleted_at) {
        this.replacement_feeding_deleted_at = replacement_feeding_deleted_at;
    }


}
