package com.example.cholomanglicmot.nativechickenandduck.DashboardDirectory;

public class FarmInfo {
    Integer id;
    String name;
    String code;
    String address;
    Integer batching_week;
    Integer breedable_id;

    public FarmInfo(Integer id, String name, String code, String address,Integer batching_week, Integer breedable_id) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.address = address;
        this.batching_week = batching_week;
        this.breedable_id = breedable_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getBatching_week() {
        return batching_week;
    }

    public void setBatching_week(Integer batching_week) {
        this.batching_week = batching_week;
    }

    public Integer getBreedable_id() {
        return breedable_id;
    }

    public void setBreedable_id(Integer breedable_id) {
        this.breedable_id = breedable_id;
    }
}
