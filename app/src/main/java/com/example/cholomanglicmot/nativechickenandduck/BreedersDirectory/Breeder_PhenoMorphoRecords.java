package com.example.cholomanglicmot.nativechickenandduck.BreedersDirectory;

public class Breeder_PhenoMorphoRecords {
    private Integer id;
    private Integer pheno_morpho_inv_id;
    private String phenomorpho_date;
    private String phenomorpho_tag;
    private String pheno_record;
    private String morpho_record;
    private Integer phenomorpho_male_quantitiy;
    private Integer phenomorpho_female_quantitiy;


    Breeder_PhenoMorphoRecords(){

    }

    Breeder_PhenoMorphoRecords(Integer id,
                               Integer pheno_morpho_inv_id,
                               String phenomorpho_date,
                               String phenomorpho_tag,
                               String pheno_record,
                               String morpho_record,
                               Integer phenomorpho_male_quantitiy,
                               Integer phenomorpho_female_quantitiy
    ){
        this.setId(id);
        this.setPheno_morpho_inv_id(pheno_morpho_inv_id);
        this.setPhenomorpho_date(phenomorpho_date);
        this.setPhenomorpho_tag(phenomorpho_tag);
        this.setPheno_record(pheno_record);
        this.setMorpho_record(morpho_record);
        this.setPhenomorpho_male_quantitiy(phenomorpho_male_quantitiy);
        this.setPhenomorpho__female_quantitiy(phenomorpho_female_quantitiy);

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPheno_morpho_inv_id() {
        return pheno_morpho_inv_id;
    }

    public void setPheno_morpho_inv_id(Integer pheno_morpho_inv_id) {
        this.pheno_morpho_inv_id = pheno_morpho_inv_id;
    }

    public String getPhenomorpho_date() {
        return phenomorpho_date;
    }

    public void setPhenomorpho_date(String phenomorpho_date) {
        this.phenomorpho_date = phenomorpho_date;
    }

    public String getPhenomorpho_tag() {
        return phenomorpho_tag;
    }

    public void setPhenomorpho_tag(String phenomorpho_tag) {
        this.phenomorpho_tag = phenomorpho_tag;
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

    public void setPhenomorpho_male_quantitiy(Integer phenomorpho_male_quantitiy) {
        this.phenomorpho_male_quantitiy = phenomorpho_male_quantitiy;
    }

    public Integer getPhenomorpho__female_quantitiy() {
        return phenomorpho_female_quantitiy;
    }

    public void setPhenomorpho__female_quantitiy(Integer phenomorpho__female_quantitiy) {
        this.phenomorpho_female_quantitiy = phenomorpho__female_quantitiy;
    }
}
