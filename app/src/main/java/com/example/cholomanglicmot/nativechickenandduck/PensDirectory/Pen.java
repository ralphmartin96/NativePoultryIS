package com.example.cholomanglicmot.nativechickenandduck.PensDirectory;

import com.google.gson.annotations.SerializedName;

public class Pen {
    @SerializedName("id")
    public Integer id;
    @SerializedName("number")
    public String pen_number;
    @SerializedName("type")
    public String pen_type;
    @SerializedName("total_capacity")
    public Integer pen_inventory;
    @SerializedName("current_capacity")
    public Integer pen_capacity;
    @SerializedName("farm_id")
    public Integer farm_id;
    @SerializedName("is_active")
    public Integer is_active;

    public Pen (Integer id, String pen_number,String pen_type,Integer pen_inventory,Integer pen_capacity, Integer farm_id, Integer is_active){
        this.setId(id);
        this.setPen_number(pen_number);
        this.setPen_type(pen_type);
        this.setPen_inventory(pen_inventory);
        this.setPen_capacity(pen_capacity);
        this.setFarm_id(farm_id);
        this.setIs_active(is_active);
    }

    public Integer getFarm_id() {
        return farm_id;
    }

    public void setFarm_id(Integer farm_id) {
        this.farm_id = farm_id;
    }

    public Integer getIs_active() {
        return is_active;
    }

    public void setIs_active(Integer is_active) {
        this.is_active = is_active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPen_number() {
        return pen_number;
    }

    public void setPen_number(String pen_number) {
        this.pen_number = pen_number;
    }

    public String getPen_type() {
        return pen_type;
    }

    public void setPen_type(String pen_type) {
        this.pen_type = pen_type;
    }

    public Integer getPen_capacity() {
        return pen_capacity;
    }

    public void setPen_capacity(Integer pen_capacity) {
        this.pen_capacity = pen_capacity;
    }

    public Integer getPen_inventory() {
        return pen_inventory;
    }

    public void setPen_inventory(Integer pen_inventory) {
        this.pen_inventory = pen_inventory;
    }
}
