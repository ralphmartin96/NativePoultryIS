package com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory;

import com.google.gson.annotations.SerializedName;

public class Breeder_PhenoMorphoView {

    public Integer id;
    public String gender;
    public String tag;
    @SerializedName("phenotypic")
    public String pheno_record;
    @SerializedName("morphometric")
    public String morpho_record;
    @SerializedName("date_collected")
    public String date;
    public String deleted_at;



    Breeder_PhenoMorphoView(){


    }

    public Breeder_PhenoMorphoView(Integer id, String gender, String tag, String pheno_record, String morpho_record, String date, String deleted_at){

        this.setId(id);
        this.setGender(gender);
        this.setTag(tag);
        this.setPheno_record(pheno_record);
        this.setMorpho_record(morpho_record);
        this.setDate(date);
        this.setDeleted_at(deleted_at);


    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPheno_record() {
        return pheno_record;
    }

    public void setPheno_record(String pheno_record) {
        this.pheno_record = pheno_record;
    }

    public String getMorpho_record() {
        return morpho_record;
    }

    public void setMorpho_record(String morpho_record) {
        this.morpho_record = morpho_record;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }
}
