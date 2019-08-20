package com.example.cholomanglicmot.nativechickenandduck;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class DataProvider {

    public static LinkedHashMap<String, List<String>> getInfo(){
        LinkedHashMap<String, List<String>> ProjectDetails = new LinkedHashMap<>();
        List<String> Dashboard = new ArrayList<String>();
        //no child

        List<String> Create_Pens = new ArrayList<String>();
        List<String> Create_Generation_And_Lines = new ArrayList<String>();
        List<String> Create_Families = new ArrayList<String>();
        List<String> Create_Breeders = new ArrayList<String>();
        List<String> Create_Brooders = new ArrayList<String>();
        List<String> Create_Replacements = new ArrayList<String>();
        List<String> Log_Out = new ArrayList<>();
        //no child

  /*      List<String> Breeder = new ArrayList<String>();
        Breeder.add("Generation");
        Breeder.add("Family Records");
        Breeder.add("Daily Records");
        Breeder.add("Hatchery Records");
        Breeder.add("Egg Quality Records");*/

/*        List<String> Replacement = new ArrayList<String>();
        Replacement.add("Add Replacement Stocks");
        Replacement.add("Phenotypic and Morphometric");
        Replacement.add("Feeding Record");*/

/*
        Create_Brooders.add("Growth Performance");
        Create_Brooders.add("Feeding Records");*/


        //no child

        List<String> Reports = new ArrayList<String>();

        List<String> Farm_Settings = new ArrayList<String>();


        ProjectDetails.put("Dashboard", Dashboard);
        ProjectDetails.put("Pens", Create_Pens);
        ProjectDetails.put("Generations and Lines", Create_Generation_And_Lines);
        ProjectDetails.put("Families", Create_Families);
        ProjectDetails.put("Breeders", Create_Breeders);
        //ProjectDetails.put("Breeders", Breeder);
        ProjectDetails.put("Replacements", Create_Replacements);
        ProjectDetails.put("Brooders", Create_Brooders);


      /*  ProjectDetails.put("Reports", Reports);*/
        ProjectDetails.put("Farm Settings", Farm_Settings);
        ProjectDetails.put("Log Out", Log_Out);



        return ProjectDetails;

    }

}
