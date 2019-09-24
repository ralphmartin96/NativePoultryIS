package com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory;

import java.util.ArrayList;
import java.util.Comparator;

public class Replacement_Pen {
    private String replacement_pen_number;
    private Integer replacement_pen_content;
    private Integer replacement_pen_free;

    ArrayList<Replacement_Inventory> arrayList;
    public Replacement_Pen(){
    }

    public String getReplacement_pen_number() {
        return replacement_pen_number;
    }

    public void setReplacement_pen_number(String replacement_pen_number) {
        this.replacement_pen_number = replacement_pen_number;
    }

    public Integer getReplacement_pen_content() {
        return replacement_pen_content;
    }

    public void setReplacement_pen_content(Integer replacement_pen_content) {
        this.replacement_pen_content = replacement_pen_content;
    }

    public Integer getReplacement_pen_free() {
        return replacement_pen_free;
    }

    public void setReplacement_pen_free(Integer replacement_pen_free) {
        this.replacement_pen_free = replacement_pen_free;
    }

    public Replacement_Pen(String replacement_pen_number, Integer replacement_pen_content, Integer replacement_pen_free) {
        this.replacement_pen_number = replacement_pen_number;
        this.replacement_pen_content = replacement_pen_content;
        this.replacement_pen_free = replacement_pen_free;

    }

    public ArrayList<Replacement_Inventory> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Replacement_Inventory> arrayList) {
        this.arrayList = arrayList;
    }

    public static Comparator<Replacement_Pen> penComparator = new Comparator<Replacement_Pen>() {

        public int compare(Replacement_Pen p1, Replacement_Pen p2) {

            String pen1 = p1.getReplacement_pen_number().toUpperCase();
            String pen2 = p2.getReplacement_pen_number().toUpperCase();

            return pen1.compareTo(pen2);
        }
    };



}
