package com.capstone.state.Controllers;

import com.capstone.state.Services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StateController {

    // ----------------------------------------------------------------------------------
    // --                                  SETUP                                       --
    // ----------------------------------------------------------------------------------

    private static String reportsPath;

    public static String getReportsPath() {
        return reportsPath;
    }

    @Autowired
    StateService stateService;



    // ----------------------------------------------------------------------------------
    // --                               MAPPINGS                                       --
    // ----------------------------------------------------------------------------------

    // Export all states
    // https://{server-address}/states?destination={destination} - to export separate files all states provided from the flat file
    @GetMapping("/states")
    public ResponseEntity<String> allStatesAPI(@RequestParam String source, @RequestParam String destination) {

        return stateService.exportAllStates(source, destination);
    }

    // Export specific stateID
    // https://{server-address}/states/{state-id}?destination={destination} - to export file for specified state provided from the flat file
    @GetMapping("/states/{stateID}")
    public ResponseEntity<String> oneStateAPI(@PathVariable String stateID, @RequestParam String source, @RequestParam String destination) {

        return stateService.exportSingleState(stateID, source, destination);
    }

    // generate state data
    @GetMapping("/generatestates")
    public ResponseEntity<String> generateStatesAPI(@RequestParam String source, @RequestParam String destination) {

        return stateService.generateStates(source, destination);
    }

    // Export top 5 transaction counts by zip code
    @GetMapping("/states/top5zipcodes")
    public ResponseEntity<String> top5ZipCodesAPI(@RequestParam String source, @RequestParam String reports_destination) {

        reportsPath = reports_destination;
        return stateService.exportTop5ZipCodes(source, reports_destination);
    }
}
