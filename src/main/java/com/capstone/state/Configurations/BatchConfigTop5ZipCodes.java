package com.capstone.state.Configurations;

import com.capstone.state.Classifiers.StateTransactionClassifier;
import com.capstone.state.Controllers.StateController;
import com.capstone.state.Listeners.CustomChunkListener;
import com.capstone.state.Models.StateTransactionModel;
import com.capstone.state.Processors.AllStatesProcessor;
import com.capstone.state.Processors.Top5ZipCodesProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

@Configuration
@Slf4j
public class BatchConfigTop5ZipCodes {

    // ----------------------------------------------------------------------------------
    // --                                  SETUP                                       --
    // ----------------------------------------------------------------------------------

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    @Qualifier("reader_StateTransaction")
    private SynchronizedItemStreamReader<StateTransactionModel> synchronizedItemStreamReader;

    @Autowired
    private Top5ZipCodesProcessor top5ZipCodesProcessor;

    @Autowired
    @Qualifier("writer_StateTransaction")
    private ClassifierCompositeItemWriter<StateTransactionModel> classifierCompositeItemWriter;

    @Autowired
    @Qualifier("taskExecutor_State")
    private org.springframework.core.task.TaskExecutor asyncTaskExecutor;

//    @Autowired
//    private StateTransactionClassifier stateTransactionClassifier;



    // ----------------------------------------------------------------------------------
    // --                             STEPS & JOBS                                     --
    // ----------------------------------------------------------------------------------

    // Step - top 5 zip codes (transaction count)
    @Bean
    public Step step_exportTop5ZipCodes() {

        return new StepBuilder("exportTop5ZipCodesStep", jobRepository)
                .<StateTransactionModel, StateTransactionModel> chunk(50000, transactionManager)
                .reader(synchronizedItemStreamReader)
                .processor(top5ZipCodesProcessor)
                .writer(classifierCompositeItemWriter)
                .listener(new CustomChunkListener())
                .listener(new StepExecutionListener() {
                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {

                        // Create reports file using reports file path from Controller API call
                        String filePath = StateController.getReportsPath();
                        File top5ZipCodesReport = new File(filePath);

                        Map<String, Long> top5ZipCodesMap = top5ZipCodesProcessor.getZipCodesMap();

                        // Write relevant data to reports file
                        try {
                            BufferedWriter writer = new BufferedWriter(new FileWriter(top5ZipCodesReport));

                            writer.write("Top 5 Transaction Counts By Zip Code");
                            writer.newLine();
                            writer.write("-------------------------------------");
                            writer.newLine();
                            writer.newLine();
                            writer.write("Zipcode - Transaction Count");
                            writer.newLine();
                            writer.newLine();
                            top5ZipCodesMap.forEach((zipCode, count) -> {
                                try {
                                    writer.write(zipCode + "\t" + count);
                                    writer.newLine();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });

                            writer.close();

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        log.info("------------------------------------------------------------------");
                        log.info(stepExecution.getSummary());
                        log.info("------------------------------------------------------------------");

                        top5ZipCodesProcessor.clearAllTrackersAndCounters();

                        return StepExecutionListener.super.afterStep(stepExecution);
                    }
                })
                .taskExecutor(asyncTaskExecutor)
                .build();
    }

    // Job - top 5 zip codes (transaction count)
    @Bean
    public Job job_exportTop5ZipCodes() {

        return new JobBuilder("exportTop5ZipCodesJob", jobRepository)
                .start(step_exportTop5ZipCodes())
                .build();
    }
}
