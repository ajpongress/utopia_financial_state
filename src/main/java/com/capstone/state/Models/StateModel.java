package com.capstone.state.Models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class StateModel {

    private long id;
    private String stateAbbrev; // CSV - Merchant State (index 10)
    private String stateFull;
    private String stateCapital;
    private ArrayList<String> stateZips = new ArrayList<>();

    public void add_StateZip(String zipcode) {
        this.stateZips.add(zipcode);
    }

    public void printZips() {
        for (String s : this.stateZips) {
            System.out.println(s);
        }
    }

    public void printState() {

        System.out.println("----------------------------------------");
        System.out.println(
                this.stateAbbrev + " - " + this.stateFull + " - " + this.stateCapital
        );
        System.out.print("Zip code prefix - "); printZips();
    }

    public String getFirstZip() {
        return stateZips.get(0);
    }
}
