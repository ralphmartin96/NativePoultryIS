package com.example.cholomanglicmot.nativechickenandduck.FamilyDirectory;

public class Family {

    private String family_number;
    private String line_number;
    private String generation_number;

    public Family(){
    }

    public Family(String family_number, String line_number, String generation_number) {
        this.setFamily_number(family_number);
        this.setLine_number(line_number);
        this.setGeneration_number(generation_number);

    }

    public String getGeneration_number() {
        return generation_number;
    }

    public void setGeneration_number(String generation_number) {
        this.generation_number = generation_number;
    }
    public String getLine_number() {
        return line_number;
    }

    public void setLine_number(String line_number) {
        this.line_number = line_number;
    }

    public String getFamily_number() {
        return family_number;
    }

    public void setFamily_number(String family_number) {
        this.family_number = family_number;
    }

}


