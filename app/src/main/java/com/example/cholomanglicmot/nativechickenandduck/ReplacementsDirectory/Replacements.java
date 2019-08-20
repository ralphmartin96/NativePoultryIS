package com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory;

import com.google.gson.annotations.SerializedName;

public class Replacements {
    @SerializedName("id")
    private Integer id;
/*    private String replacement_generation_number;
    private String replacement_line_number;*/
    @SerializedName("family_id")
    private Integer replacement_family_number; //reference dapat to familytable
    @SerializedName("date_added")
    private String replacement_date_added;
    @SerializedName("deleted_at")
    private String replacement_deleted_at;

    public Replacements(){

    }

    public Replacements(Integer id,  Integer replacement_family_number, String replacement_date_added, String replacement_deleted_at){
        this.setId(id);
/*        this.setReplacement_generation_number(getReplacement_family_number());
        this.setReplacement_line_number(replacement_line_number);*/
        this.setReplacement_family_number(replacement_family_number);
        this.setReplacement_date_added(replacement_date_added);
        this.setReplacement_deleted_at(replacement_deleted_at);
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
/*

    public String getReplacement_generation_number() {
        return replacement_generation_number;
    }

    public void setReplacement_generation_number(String replacement_generation_number) {
        this.replacement_generation_number = replacement_generation_number;
    }

    public String getReplacement_line_number() {
        return replacement_line_number;
    }

    public void setReplacement_line_number(String replacement_line_number) {
        this.replacement_line_number = replacement_line_number;
    }
*/



    public Integer getReplacement_family_number() {
        return replacement_family_number;
    }

    public void setReplacement_family_number(Integer replacement_family_number) {
        this.replacement_family_number = replacement_family_number;
    }

    public String getReplacement_date_added() {
        return replacement_date_added;
    }

    public void setReplacement_date_added(String replacement_date_added) {
        this.replacement_date_added = replacement_date_added;
    }

    public String getReplacement_deleted_at() {
        return replacement_deleted_at;
    }

    public void setReplacement_deleted_at(String replacement_deleted_at) {
        this.replacement_deleted_at = replacement_deleted_at;
    }
}
