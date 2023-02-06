package com.capstone.state.Processors;

import com.capstone.state.Models.ListOfStates;
import com.capstone.state.Models.StateArrayObject;
import com.capstone.state.Models.StateModel;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@StepScope
@Component
@Slf4j
public class StateProcessor implements ItemProcessor<StateModel, StateModel> {

    // ----------------------------------------------------------------------------------
    // --                                  SETUP                                       --
    // ----------------------------------------------------------------------------------

    private final Set<String> stateTracker = new HashSet<>();
    private final ListOfStates listOfStates = new ListOfStates();
    private final Faker faker = new Faker();

    private static long idCounter = 0;

    // ----------------------------------------------------------------------------------
    // --                                METHODS                                       --
    // ----------------------------------------------------------------------------------

    @Override
    public StateModel process(StateModel stateModel) {

        synchronized(this) {

            // avoids duplicate states being returned
            if (stateTracker.contains(stateModel.getStateAbbrev())) {
                return null;
            }

            // Add entry to tracker to filter duplicates before filtering for 2 letters
            stateTracker.add(stateModel.getStateAbbrev());

            // merchant state is a U.S. state
            if (stateModel.getStateAbbrev().length() == 2) {

                // Set state object parameters
                for (StateArrayObject sao : listOfStates.getStateArrayList()) {

                    // Check state object abbreviation (from CSV file) against the ListOfStates class to find a match
                    if (stateModel.getStateAbbrev().equals(sao.getStateAbbreviation())) {

                        stateModel.setStateFull(sao.getStateFullName()); // Set state full name
                        stateModel.setStateCapital(sao.getStateCapitalName()); // Set state capital
                        stateModel.add_StateZip(faker.address().zipCodeByState(stateModel.getStateAbbrev())); // Set state zip prefix
                    }
                }

                // Update id counter, print state object when done processing and return
                idCounter++;
                stateModel.setId(idCounter);
                stateModel.printState();
                return stateModel;

            }
            else return null; // merchant state isn't part of the 50 U.S. states
        }
    }
}
