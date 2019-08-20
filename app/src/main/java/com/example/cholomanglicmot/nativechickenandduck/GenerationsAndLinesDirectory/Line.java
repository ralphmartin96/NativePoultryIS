package com.example.cholomanglicmot.nativechickenandduck.GenerationsAndLinesDirectory;

import com.google.gson.annotations.SerializedName;
/**/
public class Line {
    @SerializedName("id")
    private Integer id;
    @SerializedName("number")
    private String line_number;
    @SerializedName("generation_id")
    private Integer generation_number;

    public Line(){

    }
    public Line(Integer id,String line_number,Integer generation_number) {
        this.setId(id);
        this.setGeneration_number(generation_number);
        this.setLine_number(line_number);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGeneration_number() {
        return generation_number;
    }

    public void setGeneration_number(Integer generation_number) {
        this.generation_number = generation_number;
    }
    public String getLine_number() {
        return line_number;
    }

    public void setLine_number(String line_number) {
        this.line_number = line_number;
    }

}


