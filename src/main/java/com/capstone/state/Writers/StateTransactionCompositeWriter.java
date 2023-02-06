package com.capstone.state.Writers;

import com.capstone.state.Classifiers.StateTransactionClassifier;
import com.capstone.state.Models.StateTransactionModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StateTransactionCompositeWriter {

    // ----------------------------------------------------------------------------------
    // --                                  SETUP                                       --
    // ----------------------------------------------------------------------------------

    @Autowired
    StateTransactionClassifier stateTransactionClassifier;



    // ----------------------------------------------------------------------------------
    // --                                METHODS                                       --
    // ----------------------------------------------------------------------------------

    @Bean("writer_StateTransaction")
    public ClassifierCompositeItemWriter<StateTransactionModel> classifierCompositeItemWriter() {

        ClassifierCompositeItemWriter<StateTransactionModel> writer = new ClassifierCompositeItemWriter<>();
        writer.setClassifier(stateTransactionClassifier);

        return writer;
    }
}
