package com.capstone.state.Processors;

import com.capstone.state.Models.StateModel;
import com.capstone.state.Models.StateTransactionModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.*;
import static java.util.Map.Entry.*;

@Component
@Slf4j
public class Top5ZipCodesProcessor implements ItemProcessor<StateTransactionModel, StateTransactionModel> {

    // ----------------------------------------------------------------------------------
    // --                                  SETUP                                       --
    // ----------------------------------------------------------------------------------

    // Tracks zip codes and increments counter
    // Primary key = zip code (string)
    // Value = counter
    private Map<String, Long> zipCodeMap = new HashMap<>();

    // Return hashmap (sorted by count)
    public Map<String, Long> getZipCodesMap() {

        Map<String, Long> sorted = zipCodeMap
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(5)
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new)
                )
        ;
        return sorted;
    }

    private long tempCounter = 0L;

    public void clearAllTrackersAndCounters() {

        zipCodeMap.clear();
        tempCounter = 0L;
    }

    // ----------------------------------------------------------------------------------
    // --                                METHODS                                       --
    // ----------------------------------------------------------------------------------

    public StateTransactionModel process(StateTransactionModel transaction) {

        synchronized (this) {

            // Strip fractional part of TransactionZip if greater than 5 characters
            if (transaction.getTransactionZip().length() > 5) {
                String[] temp_TransactionZip = transaction.getTransactionZip().split("\\.", 0);
                transaction.setTransactionZip(temp_TransactionZip[0]);
            }

            // Strip negative sign from MerchantID
            long temp_MerchantID = Math.abs(transaction.getMerchantID());
            transaction.setMerchantID(temp_MerchantID);

            String zipCode = transaction.getTransactionZip();

            // Check zipcode string isn't empty (online transaction)
            if (!zipCode.equals("")) {

                // New zipcode - set counter to 1
                if (!zipCodeMap.containsKey(zipCode)) {

                    zipCodeMap.put(zipCode, 1L);
                }

                // Zipcode already exists in hashmap
                else {

                    // Get counter and increment it
                    tempCounter = zipCodeMap.get(zipCode);
                    tempCounter++;
                    zipCodeMap.replace(zipCode, tempCounter);
                }
            }



            return null; // don't return to writer. Only a report will get generated at end of step
        }
    }
}
