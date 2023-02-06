package com.capstone.state.Controllers;

import com.capstone.state.Services.StateTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StateTransactionController {

    // ----------------------------------------------------------------------------------
    // --                                  SETUP                                       --
    // ----------------------------------------------------------------------------------

    @Autowired
    StateTransactionService stateTransactionService;



    // ----------------------------------------------------------------------------------
    // --                               MAPPINGS                                       --
    // ----------------------------------------------------------------------------------

    // Export all states
    // https://{server-address}/states?destination={destination} - to export separate files all states provided from the flat file
    @GetMapping("/states")
    public ResponseEntity<String> allStatesAPI(@RequestParam String source, @RequestParam String destination) {

        return stateTransactionService.exportAllStates(source, destination);
    }

    // Export specific stateID
    // https://{server-address}/states/{state-id}?destination={destination} - to export file for specified state provided from the flat file
    @GetMapping("/states/{stateID}")
    public ResponseEntity<String> oneStateAPI(@PathVariable String stateID, @RequestParam String source, @RequestParam String destination) {

        return stateTransactionService.exportSingleState(stateID, source, destination);
    }
}
