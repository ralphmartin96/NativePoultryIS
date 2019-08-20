package com.example.cholomanglicmot.nativechickenandduck.GenerationsAndLinesDirectory;

import com.google.gson.annotations.SerializedName;

/*    public static final String GENERATION_COL_0 = "ID";
public static final String GENERATION_COL_1 = "farm_id";
public static final String GENERATION_COL_2 = "GENERATION_NUMBER";
public static final String GENERATION_COL_3 = "numerical_generation";
public static final String GENERATION_COL_4 = "GENERATION_STATUS";
public static final String GENERATION_COL_5 = "GENERATION_CULL";*/
public class Generation {
    @SerializedName("number")
    private String generation_number;
    @SerializedName("is_active")
    private Integer generation_status;
    @SerializedName("id")
    private Integer id;
    @SerializedName("farm_id")
    private Integer farm_id;
    @SerializedName("numerical_generation")
    private Integer numerical_generation;
    @SerializedName("deleted_at")
    private String deleted_at;




    public Generation(){

    }

    public Generation(String generation_number, Integer generation_status, Integer id, Integer farm_id, Integer numerical_generation, String deleted_at) {
        this.setGeneration_number(generation_number);
        this.setGeneration_status(generation_status);
        this.setId(id);
        this.setFarm_id(farm_id);
        this.setNumerical_generation(numerical_generation);

        this.setDeleted_at(deleted_at);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFarm_id() {
        return farm_id;
    }

    public void setFarm_id(Integer farm_id) {
        this.farm_id = farm_id;
    }

    public Integer getNumerical_generation() {
        return numerical_generation;
    }

    public void setNumerical_generation(Integer numerical_generation) {
        this.numerical_generation = numerical_generation;
    }



    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getGeneration_number() {
        return generation_number;
    }

    public void setGeneration_number(String generation_number) {
        this.generation_number = generation_number;
    }

    public Integer getGeneration_status() {
        return generation_status;
    }

    public void setGeneration_status(Integer generation_status) {
        this.generation_status = generation_status;
    }
}


