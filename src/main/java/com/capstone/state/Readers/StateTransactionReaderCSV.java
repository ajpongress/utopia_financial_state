package com.capstone.state.Readers;

import com.capstone.state.Models.StateTransactionModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.batch.item.support.builder.SynchronizedItemStreamReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StateTransactionReaderCSV {

    // FlatFileItemReader
    @StepScope
    @Bean("reader_StateTransaction")
    public SynchronizedItemStreamReader<StateTransactionModel> synchronizedItemStreamReader(
            @Value("#{jobParameters['file.input']}")
            String source_input
    ) throws UnexpectedInputException,
            NonTransientResourceException, ParseException {

        FlatFileItemReader<StateTransactionModel> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource(source_input));
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper((line, lineNumber) -> {
            String[] fields = line.split(",");
            StateTransactionModel stateTransactionModel = new StateTransactionModel();

            stateTransactionModel.setUserID(Long.parseLong(fields[0]));
            stateTransactionModel.setCardID(Long.parseLong(fields[1]));
            stateTransactionModel.setTransactionYear(fields[2]);
            stateTransactionModel.setTransactionMonth(fields[3]);
            stateTransactionModel.setTransactionDay(fields[4]);
            stateTransactionModel.setTransactionTime(fields[5]);
            stateTransactionModel.setTransactionAmount(fields[6]);
            stateTransactionModel.setTransactionType(fields[7]);
            stateTransactionModel.setMerchantID(Long.parseLong(fields[8]));
            stateTransactionModel.setTransactionCity(fields[9]);
            stateTransactionModel.setTransactionState(fields[10]);
            stateTransactionModel.setTransactionZip(fields[11]);
            stateTransactionModel.setMerchantCatCode(Long.parseLong(fields[12]));
            stateTransactionModel.setTransactionErrorCheck(fields[13]);
            stateTransactionModel.setTransactionFraudCheck(fields[14]);

            return stateTransactionModel;
        });

        // Make FlatFileItemReader thread-safe
        return new SynchronizedItemStreamReaderBuilder<StateTransactionModel>()
                .delegate(itemReader)
                .build();
    }
}
