package com.capstone.state.Controllers;

import com.capstone.state.Services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StateController {

    // ----------------------------------------------------------------------------------
    // --                                  SETUP                                       --
    // ----------------------------------------------------------------------------------

    @Autowired
    StateService stateService;



    // ----------------------------------------------------------------------------------
    // --                               MAPPINGS                                       --
    // ----------------------------------------------------------------------------------

    // generate state data
    @GetMapping("/generatestates")
    public ResponseEntity<String> generateStatesAPI(@RequestParam String source, @RequestParam String destination) {

        return stateService.generateStates(source, destination);
    }
}
