package com.example.cholomanglicmot.nativechickenandduck.ReplacementsDirectory;
/*                              inv_id, pheno_date, pheno_sex, pheno_tag, pheno_record, morphos);*/
public class Replacement_PhenoMorphoView {
    private Integer id;
    private String gender;
    private String tag;
    private String pheno_record;
    private String morpho_record;
    private String date;
    private String deleted_at;



    Replacement_PhenoMorphoView(){


    }

    Replacement_PhenoMorphoView(Integer id, String gender, String tag, String pheno_record, String morpho_record, String date, String deleted_at){

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
