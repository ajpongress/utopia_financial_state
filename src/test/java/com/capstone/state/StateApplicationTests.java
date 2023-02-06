package com.capstone.state;

import com.capstone.state.Models.StateModel;
import com.capstone.state.Models.StateTransactionModel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StateApplicationTests {

    @Test
    void contextLoads() {
    }

    // ----------------------------------------------------------------------------------
    // --                             MODEL TESTING                                    --
    // ----------------------------------------------------------------------------------

    // StateModel test
    @Test
    public void creates_state_id_1_abbreviation_CA_fullname_California_capital_Sacramento_zip_99999() throws ClassNotFoundException {

        StateModel stateModel = new StateModel();

        stateModel.setId(1);
        stateModel.setStateAbbrev("CA");
        stateModel.setStateFull("California");
        stateModel.setStateCapital("Sacramento");
        stateModel.add_StateZip("99999");

        assertEquals(1, stateModel.getId());
        assertEquals("CA", stateModel.getStateAbbrev());
        assertEquals("California", stateModel.getStateFull());
        assertEquals("Sacramento", stateModel.getStateCapital());
        assertEquals("99999", stateModel.getFirstZip());
    }

    // StateTransactionModel test
    @Test
    public void creates_transaction_id_1_userid_99_cardid_88_year_2023_month_3_day_7_time_1130_amount_101_11_type_swipe_transaction_merchantid_777777777_city_Chicago_state_IL_zip_60602_merchantcode_5555_error_no_fraud_no() throws ClassNotFoundException {

        StateTransactionModel stateTransactionModel = new StateTransactionModel();
        stateTransactionModel.setId(1);
        stateTransactionModel.setUserID(99);
        stateTransactionModel.setCardID(88);
        stateTransactionModel.setTransactionYear("2023");
        stateTransactionModel.setTransactionMonth("3");
        stateTransactionModel.setTransactionDay("7");
        stateTransactionModel.setTransactionTime("11:30");
        stateTransactionModel.setTransactionAmount("$101.11");
        stateTransactionModel.setTransactionType("Swipe UserTransactionModel");
        stateTransactionModel.setMerchantID(777777777);
        stateTransactionModel.setTransactionCity("Chicago");
        stateTransactionModel.setTransactionState("IL");
        stateTransactionModel.setTransactionZip("60602");
        stateTransactionModel.setMerchantCatCode(5555);
        stateTransactionModel.setTransactionErrorCheck("Yes");
        stateTransactionModel.setTransactionFraudCheck("No");

        assertEquals(1, stateTransactionModel.getId());
        assertEquals(99, stateTransactionModel.getUserID());
        assertEquals(88, stateTransactionModel.getCardID());
        assertEquals("2023", stateTransactionModel.getTransactionYear());
        assertEquals("3", stateTransactionModel.getTransactionMonth());
        assertEquals("7", stateTransactionModel.getTransactionDay());
        assertEquals("11:30", stateTransactionModel.getTransactionTime());
        assertEquals("$101.11", stateTransactionModel.getTransactionAmount());
        assertEquals("Swipe UserTransactionModel", stateTransactionModel.getTransactionType());
        assertEquals(777777777, stateTransactionModel.getMerchantID());
        assertEquals("Chicago", stateTransactionModel.getTransactionCity());
        assertEquals("IL", stateTransactionModel.getTransactionState());
        assertEquals("60602", stateTransactionModel.getTransactionZip());
        assertEquals(5555, stateTransactionModel.getMerchantCatCode());
        assertEquals("Yes", stateTransactionModel.getTransactionErrorCheck());
        assertEquals("No", stateTransactionModel.getTransactionFraudCheck());
    }
}
