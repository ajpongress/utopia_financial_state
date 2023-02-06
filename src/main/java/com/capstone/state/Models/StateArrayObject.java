package com.capstone.state.Models;

public class StateArrayObject {

    private final String stateAbbreviation;
    private String stateFullName;
    private String stateCapitalName;

    public StateArrayObject (String newStateAbbreviation, String newStateFullName, String newStateCapitalName) {
        this.stateAbbreviation = newStateAbbreviation;
        this.stateFullName = newStateFullName;
        this.stateCapitalName = newStateCapitalName;
    }

    public String getStateAbbreviation() {
        return this.stateAbbreviation;
    }

    public String getStateFullName() {
        return this.stateFullName;
    }

    public String getStateCapitalName() {
        return this.stateCapitalName;
    }
}
