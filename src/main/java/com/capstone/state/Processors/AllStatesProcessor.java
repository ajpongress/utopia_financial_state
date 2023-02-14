package com.capstone.state.Processors;

import com.capstone.state.Models.StateTransactionModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
public class AllStatesProcessor implements ItemProcessor<StateTransactionModel, StateTransactionModel> {

    // ----------------------------------------------------------------------------------
    // --                                  SETUP                                       --
    // ----------------------------------------------------------------------------------

    private final HashMap<Long, String> stateTransactionMap = new HashMap<>();

    private static long transactionIdCounter = 0;

    // Useful for additional jobs or steps
    public void clearAllTrackersAndCounters() {

        stateTransactionMap.clear();
        transactionIdCounter = 0;
    }

    // ----------------------------------------------------------------------------------
    // --                                METHODS                                       --
    // ----------------------------------------------------------------------------------

    public StateTransactionModel process(StateTransactionModel stateTransactionModel) {

        synchronized (this) {

            // Strip negative sign from MerchantID
            long temp_MerchantID = Math.abs(stateTransactionModel.getMerchantID());
            stateTransactionModel.setMerchantID(temp_MerchantID);

            // Strip fractional part of TransactionZip if greater than 5 characters
            if (stateTransactionModel.getTransactionZip().length() > 5) {
                String[] temp_TransactionZip = stateTransactionModel.getTransactionZip().split("\\.", 0);
                stateTransactionModel.setTransactionZip(temp_TransactionZip[0]);
            }

            // Print processed transaction and return
            transactionIdCounter++;
            stateTransactionModel.setId(transactionIdCounter);
            log.info(stateTransactionModel.toString());
            return stateTransactionModel;

        }


    }
}
