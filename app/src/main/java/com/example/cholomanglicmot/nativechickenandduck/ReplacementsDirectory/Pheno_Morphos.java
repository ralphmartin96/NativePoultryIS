package com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory;

import com.google.gson.annotations.SerializedName;

public class Pheno_Morphos {
    @SerializedName("id")
    private Integer id;
    @SerializedName("replacement_inventory_id")
    private Integer replacement_inventory;
    @SerializedName("breeder_inventory_id")
    private Integer breeder_inventory;
    @SerializedName("values_id")
    private Integer values_id;
    @SerializedName("deleted_at")
    private String deleted_at;

    public Pheno_Morphos(){

    }

    public Pheno_Morphos(Integer id, Integer replacement_inventory, Integer breeder_inventory, Integer values_id, String deleted_at){
        this.setId(id);
        this.setReplacement_inventory(replacement_inventory);
        this.setBreeder_inventory(breeder_inventory);
        this.setValues_id(values_id);
        this.setDeleted_at(deleted_at);
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReplacement_inventory() {
        return replacement_inventory;
    }

    public void setReplacement_inventory(Integer replacement_inventory) {
        this.replacement_inventory = replacement_inventory;
    }

    public Integer getBreeder_inventory() {
        return breeder_inventory;
    }

    public void setBreeder_inventory(Integer breeder_inventory) {
        this.breeder_inventory = breeder_inventory;
    }

    public Integer getValues_id() {
        return values_id;
    }

    public void setValues_id(Integer values_id) {
        this.values_id = values_id;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }
}
