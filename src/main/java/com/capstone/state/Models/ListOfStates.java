package com.capstone.state.Models;

import java.util.ArrayList;

public class ListOfStates {

    private final ArrayList<StateArrayObject> stateArrayList = new ArrayList<>();

    public ListOfStates() {

        StateArrayObject stateArrayObject_Alabama = new StateArrayObject("AL", "Alabama", "Montgomery");
        StateArrayObject stateArrayObject_Alaska = new StateArrayObject("AK", "Alaska", "Juneau");
        StateArrayObject stateArrayObject_Arizona = new StateArrayObject("AZ", "Arizona", "Phoenix");
        StateArrayObject stateArrayObject_Arkansas = new StateArrayObject("AR", "Arkansas", "Little Rock");
        StateArrayObject stateArrayObject_California = new StateArrayObject("CA", "California", "Sacramento");

        StateArrayObject stateArrayObject_Colorado = new StateArrayObject("CO", "Colorado", "Denver");
        StateArrayObject stateArrayObject_Connecticut = new StateArrayObject("CT", "Connecticut", "Hartford");
        StateArrayObject stateArrayObject_Delaware = new StateArrayObject("DE", "Delaware", "Dover");
        StateArrayObject stateArrayObject_Florida = new StateArrayObject("FL", "Florida", "Tallahassee");
        StateArrayObject stateArrayObject_Georgia = new StateArrayObject("GA", "Georgia", "Atlanta");

        StateArrayObject stateArrayObject_Hawaii = new StateArrayObject("HI", "Hawaii", "Honolulu");
        StateArrayObject stateArrayObject_Idaho = new StateArrayObject("ID", "Idaho", "Boise");
        StateArrayObject stateArrayObject_Illinois = new StateArrayObject("IL", "Illinois", "Springfield");
        StateArrayObject stateArrayObject_Indiana = new StateArrayObject("IN", "Indiana", "Indianapolis");
        StateArrayObject stateArrayObject_Iowa = new StateArrayObject("IA", "Iowa", "Des Moines");

        StateArrayObject stateArrayObject_Kansas = new StateArrayObject("KS", "Kansas", "Topeka");
        StateArrayObject stateArrayObject_Kentucky = new StateArrayObject("KY", "Kentucky", "Frankfort");
        StateArrayObject stateArrayObject_Louisiana = new StateArrayObject("LA", "Louisiana", "Baton Rouge");
        StateArrayObject stateArrayObject_Maine = new StateArrayObject("ME", "Maine", "Augusta");
        StateArrayObject stateArrayObject_Maryland = new StateArrayObject("MD", "Maryland", "Annapolis");

        StateArrayObject stateArrayObject_Massachusetts = new StateArrayObject("MA", "Massachusetts", "Boston");
        StateArrayObject stateArrayObject_Michigan = new StateArrayObject("MI", "Michigan", "Lansing");
        StateArrayObject stateArrayObject_Minnesota = new StateArrayObject("MN", "Minnesota", "Saint Paul");
        StateArrayObject stateArrayObject_Mississippi = new StateArrayObject("MS", "Mississippi", "Jackson");
        StateArrayObject stateArrayObject_Missouri = new StateArrayObject("MO", "Missouri", "Jefferson City");

        StateArrayObject stateArrayObject_Montana = new StateArrayObject("MT", "Montana", "Helena");
        StateArrayObject stateArrayObject_Nebraska = new StateArrayObject("NE", "Nebraska", "Lincoln");
        StateArrayObject stateArrayObject_Nevada = new StateArrayObject("NV", "Nevada", "Carson City");
        StateArrayObject stateArrayObject_NewHampshire = new StateArrayObject("NH", "New Hampshire", "Concord");
        StateArrayObject stateArrayObject_NewJersey = new StateArrayObject("NJ", "New Jersey", "Trenton");

        StateArrayObject stateArrayObject_NewMexico = new StateArrayObject("NM", "New Mexico", "Santa Fe");
        StateArrayObject stateArrayObject_NewYork = new StateArrayObject("NY", "New York", "Albany");
        StateArrayObject stateArrayObject_NorthCarolina = new StateArrayObject("NC", "North Carolina", "Raleigh");
        StateArrayObject stateArrayObject_NorthDakota = new StateArrayObject("ND", "North Dakota", "Bismarck");
        StateArrayObject stateArrayObject_Ohio = new StateArrayObject("OH", "Ohio", "Columbus");

        StateArrayObject stateArrayObject_Oklahoma = new StateArrayObject("OK", "Oklahoma", "Oklahoma City");
        StateArrayObject stateArrayObject_Oregon = new StateArrayObject("OR", "Oregon", "Salem");
        StateArrayObject stateArrayObject_Pennsylvania = new StateArrayObject("PA", "Pennsylvania", "Harrisburg");
        StateArrayObject stateArrayObject_RhodeIsland = new StateArrayObject("RI", "Rhode Island", "Providence");
        StateArrayObject stateArrayObject_SouthCarolina = new StateArrayObject("SC", "South Carolina", "Columbia");

        StateArrayObject stateArrayObject_SouthDakota = new StateArrayObject("SD", "South Dakota", "Pierre");
        StateArrayObject stateArrayObject_Tennessee = new StateArrayObject("TN", "Tennessee", "Nashville");
        StateArrayObject stateArrayObject_Texas = new StateArrayObject("TX", "Texas", "Austin");
        StateArrayObject stateArrayObject_Utah = new StateArrayObject("UT", "Utah", "Salt Lake City");
        StateArrayObject stateArrayObject_Vermont = new StateArrayObject("VT", "Vermont", "Montpelier");

        StateArrayObject stateArrayObject_Virginia = new StateArrayObject("VA", "Virginia", "Richmond");
        StateArrayObject stateArrayObject_Washington = new StateArrayObject("WA", "Washington", "Olympia");
        StateArrayObject stateArrayObject_WestVirginia = new StateArrayObject("WV", "West Virginia", "Charleston");
        StateArrayObject stateArrayObject_Wisconsin = new StateArrayObject("WI", "Wisconsin", "Madison");
        StateArrayObject stateArrayObject_Wyoming = new StateArrayObject("WY", "Wyoming", "Cheyenne");

        StateArrayObject stateArrayObject_WashingtonDC = new StateArrayObject("DC", "Washington D.C.", "Washington D.C.");

        this.stateArrayList.add(stateArrayObject_Alabama);
        this.stateArrayList.add(stateArrayObject_Alaska);
        this.stateArrayList.add(stateArrayObject_Arizona);
        this.stateArrayList.add(stateArrayObject_Arkansas);
        this.stateArrayList.add(stateArrayObject_California);

        this.stateArrayList.add(stateArrayObject_Colorado);
        this.stateArrayList.add(stateArrayObject_Connecticut);
        this.stateArrayList.add(stateArrayObject_Delaware);
        this.stateArrayList.add(stateArrayObject_Florida);
        this.stateArrayList.add(stateArrayObject_Georgia);

        this.stateArrayList.add(stateArrayObject_Hawaii);
        this.stateArrayList.add(stateArrayObject_Idaho);
        this.stateArrayList.add(stateArrayObject_Illinois);
        this.stateArrayList.add(stateArrayObject_Indiana);
        this.stateArrayList.add(stateArrayObject_Iowa);

        this.stateArrayList.add(stateArrayObject_Kansas);
        this.stateArrayList.add(stateArrayObject_Kentucky);
        this.stateArrayList.add(stateArrayObject_Louisiana);
        this.stateArrayList.add(stateArrayObject_Maine);
        this.stateArrayList.add(stateArrayObject_Maryland);

        this.stateArrayList.add(stateArrayObject_Massachusetts);
        this.stateArrayList.add(stateArrayObject_Michigan);
        this.stateArrayList.add(stateArrayObject_Minnesota);
        this.stateArrayList.add(stateArrayObject_Mississippi);
        this.stateArrayList.add(stateArrayObject_Missouri);

        this.stateArrayList.add(stateArrayObject_Montana);
        this.stateArrayList.add(stateArrayObject_Nebraska);
        this.stateArrayList.add(stateArrayObject_Nevada);
        this.stateArrayList.add(stateArrayObject_NewHampshire);
        this.stateArrayList.add(stateArrayObject_NewJersey);

        this.stateArrayList.add(stateArrayObject_NewMexico);
        this.stateArrayList.add(stateArrayObject_NewYork);
        this.stateArrayList.add(stateArrayObject_NorthCarolina);
        this.stateArrayList.add(stateArrayObject_NorthDakota);
        this.stateArrayList.add(stateArrayObject_Ohio);

        this.stateArrayList.add(stateArrayObject_Oklahoma);
        this.stateArrayList.add(stateArrayObject_Oregon);
        this.stateArrayList.add(stateArrayObject_Pennsylvania);
        this.stateArrayList.add(stateArrayObject_RhodeIsland);
        this.stateArrayList.add(stateArrayObject_SouthCarolina);

        this.stateArrayList.add(stateArrayObject_SouthDakota);
        this.stateArrayList.add(stateArrayObject_Tennessee);
        this.stateArrayList.add(stateArrayObject_Texas);
        this.stateArrayList.add(stateArrayObject_Utah);
        this.stateArrayList.add(stateArrayObject_Vermont);

        this.stateArrayList.add(stateArrayObject_Virginia);
        this.stateArrayList.add(stateArrayObject_Washington);
        this.stateArrayList.add(stateArrayObject_WestVirginia);
        this.stateArrayList.add(stateArrayObject_Wisconsin);
        this.stateArrayList.add(stateArrayObject_Wyoming);

        this.stateArrayList.add(stateArrayObject_WashingtonDC);
    }

    public ArrayList<StateArrayObject> getStateArrayList() {
        return this.stateArrayList;
    }

}
