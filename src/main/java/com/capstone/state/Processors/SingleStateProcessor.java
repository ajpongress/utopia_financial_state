package com.capstone.state.Processors;

import com.capstone.state.Models.StateTransactionModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@StepScope
@Component
@Slf4j
public class SingleStateProcessor implements ItemProcessor<StateTransactionModel, StateTransactionModel> {

    // ----------------------------------------------------------------------------------
    // --                                  SETUP                                       --
    // ----------------------------------------------------------------------------------

    // StateID for specific state transaction export
    @Value("#{jobParameters['stateID_param']}")
    private String stateID_from_URI;

    private static long transactionIdCounter = 0;

    // ----------------------------------------------------------------------------------
    // --                                METHODS                                       --
    // ----------------------------------------------------------------------------------

    public StateTransactionModel process(StateTransactionModel stateTransactionModel) {

        synchronized (this) {

            // Filter transactions by requested StateID from REST call
            if (stateTransactionModel.getTransactionState().equals(stateID_from_URI)) {

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

            // Discard all other transactions that aren't the requested stateID
            else return null;
        }
    }
}
