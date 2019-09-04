package com.example.cholomanglicmot.nativechickenandduck.FamilyDirectory;

import com.example.cholomanglicmot.nativechickenandduck.PensDirectory.Pen;

import java.util.Comparator;

public class Family1 {
    private Integer id;
    private String number;
    private Integer is_active;
    private Integer line_id;
    private String deleted_at;

    public Family1(){

    }

    public Family1(Integer id, String number, Integer is_active, Integer line_id, String deleted_at) {
        this.id = id;
        this.number = number;
        this.is_active = is_active;
        this.line_id = line_id;
        this.deleted_at = deleted_at;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getIs_active() {
        return is_active;
    }

    public void setIs_active(Integer is_active) {
        this.is_active = is_active;
    }

    public Integer getLine_id() {
        return line_id;
    }

    public void setLine_id(Integer line_id) {
        this.line_id = line_id;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public static Comparator<Family1> familyComparator = new Comparator<Family1>() {

        public int compare(Family1 f1, Family1 f2) {

            int family1 = f1.getLine_id();
            int family2= f2.getLine_id();


            return family1-(family2);
        }
    };
}
