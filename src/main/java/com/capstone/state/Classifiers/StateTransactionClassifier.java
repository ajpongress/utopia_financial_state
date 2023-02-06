package com.capstone.state.Classifiers;

import com.capstone.state.Models.StateTransactionModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.SynchronizedItemStreamWriter;
import org.springframework.batch.item.support.builder.SynchronizedItemStreamWriterBuilder;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.classify.Classifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@StepScope
@Component
@Slf4j
public class StateTransactionClassifier implements Classifier<StateTransactionModel, ItemWriter<? super StateTransactionModel>> {

    // ----------------------------------------------------------------------------------
    // --                                  SETUP                                       --
    // ----------------------------------------------------------------------------------

    // Destination path for export file
    @Value("#{jobParameters['outputPath_param']}")
    private String outputPath;

    // Map for mapping each stateID to its own dedicated ItemWriter (for performance)
    private final Map<String, ItemWriter<? super StateTransactionModel>> writerMap;

    // Public constructor
    public StateTransactionClassifier() {
        this.writerMap = new HashMap<>();
    }



    // ----------------------------------------------------------------------------------
    // --                                METHODS                                       --
    // ----------------------------------------------------------------------------------

    // Classify method (contains XML writer and synchronized item stream writer)
    @Override
    public ItemWriter<? super StateTransactionModel> classify(StateTransactionModel stateTransactionModel) {

        // Set filename to specific stateID from the Transaction model
        String fileName = stateTransactionModel.getFileName();

        // Make entire process thead-safe
        synchronized (this) {

            // If stateID has already been accessed, use the same ItemWriter
            if (writerMap.containsKey(fileName)) {
                return writerMap.get(fileName);
            }
            // Create new ItemWriter for new StateID
            else {

                // Complete path for file export
                File file = new File(outputPath + "\\" + fileName);

                // XML writer
                XStreamMarshaller marshaller = new XStreamMarshaller();
                marshaller.setAliases(Collections.singletonMap("transaction", StateTransactionModel.class));

                StaxEventItemWriter<StateTransactionModel> writerXML = new StaxEventItemWriterBuilder<StateTransactionModel>()
                        .name("stateXmlWriter")
                        .resource(new FileSystemResource(file))
                        .marshaller(marshaller)
                        .rootTagName("transactions")
                        .transactional(false) // Keeps XML headers on all output files
                        .build();

                // Make XML writer thread-safe
                SynchronizedItemStreamWriter<StateTransactionModel> synchronizedItemStreamWriter =
                        new SynchronizedItemStreamWriterBuilder<StateTransactionModel>()
                                .delegate(writerXML)
                                .build();

                writerXML.open(new ExecutionContext());
                writerMap.put(fileName, synchronizedItemStreamWriter); // Pair StateID to unique ItemWriter
                return synchronizedItemStreamWriter;
            }
        }
    }

    public void closeAllwriters() {

        for (String key : writerMap.keySet()) {

            SynchronizedItemStreamWriter<StateTransactionModel> writer = (SynchronizedItemStreamWriter<StateTransactionModel>) writerMap.get(key);
            writer.close();
        }
        writerMap.clear();
    }
}
