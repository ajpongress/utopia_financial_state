package com.capstone.state.Writers;

import com.capstone.state.Models.StateModel;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.support.SynchronizedItemStreamWriter;
import org.springframework.batch.item.support.builder.SynchronizedItemStreamWriterBuilder;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.stereotype.Component;

import java.util.Collections;

@StepScope
@Component
public class StateWriterXML {

    // ----------------------------------------------------------------------------------
    // --                                  SETUP                                       --
    // ----------------------------------------------------------------------------------

    // Destination path for export file
    @Value("#{jobParameters['outputPath_param']}")
    private String outputPath;



    // ----------------------------------------------------------------------------------
    // --                                METHODS                                       --
    // ----------------------------------------------------------------------------------

    // XML Writer - generate list of US states and state data
    @StepScope
    @Bean("writer_State")
    public SynchronizedItemStreamWriter<StateModel> xmlWriter() {

        XStreamMarshaller marshaller = new XStreamMarshaller();
        marshaller.setAliases(Collections.singletonMap("state", StateModel.class));

        StaxEventItemWriter<StateModel> writer = new StaxEventItemWriterBuilder<StateModel>()
                .name("userXmlWriter")
                .resource(new FileSystemResource(outputPath + "/state_list.xml"))
                .marshaller(marshaller)
                .rootTagName("states")
                .build();

        // Make XML writer thread-safe
        SynchronizedItemStreamWriter<StateModel> synchronizedItemStreamWriter =
                new SynchronizedItemStreamWriterBuilder<StateModel>()
                        .delegate(writer)
                        .build();
        return synchronizedItemStreamWriter;
    }
}
