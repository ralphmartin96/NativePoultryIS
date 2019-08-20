package com.example.cholomanglicmot.nativechickenandduck.BroodersDirectory;

import java.util.ArrayList;

public class Brooders_Pen {
    private String brooder_pen_number;
    private Integer brooder_pen_content;
    private Integer brooder_pen_free;

    ArrayList<Brooder_Inventory> arrayList;
    public Brooders_Pen(){
    }

    public String getBrooder_pen_number() {
        return brooder_pen_number;
    }

    public void setBrooder_pen_number(String brooder_pen_number) {
        this.brooder_pen_number = brooder_pen_number;
    }

    public Integer getBrooder_pen_content() {
        return brooder_pen_content;
    }

    public void setBrooder_pen_content(Integer brooder_pen_content) {
        this.brooder_pen_content = brooder_pen_content;
    }

    public Integer getBrooder_pen_free() {
        return brooder_pen_free;
    }

    public void setBrooder_pen_free(Integer brooder_pen_free) {
        this.brooder_pen_free = brooder_pen_free;
    }

    public Brooders_Pen(String brooder_pen_number, Integer brooder_pen_content, Integer brooder_pen_free) {
        this.brooder_pen_number = brooder_pen_number;
        this. brooder_pen_content = brooder_pen_content;
        this.brooder_pen_free = brooder_pen_free;

    }

    public ArrayList<Brooder_Inventory> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Brooder_Inventory> arrayList) {
        this.arrayList = arrayList;
    }



}
