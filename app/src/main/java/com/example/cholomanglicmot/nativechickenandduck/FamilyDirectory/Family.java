package com.example.cholomanglicmot.nativechickenandduck.FamilyDirectory;

import java.util.Comparator;

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

    public static Comparator<Family> familyComparator = new Comparator<Family>() {

        public int compare(Family f1, Family f2) {

            String family1 = f1.getGeneration_number();
            String family2 = f2.getGeneration_number();

            return family1.compareTo(family2);
        }
    };

}


