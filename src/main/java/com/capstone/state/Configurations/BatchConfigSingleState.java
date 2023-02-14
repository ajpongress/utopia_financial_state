package com.capstone.state.Configurations;

import com.capstone.state.Classifiers.StateTransactionClassifier;
import com.capstone.state.Models.StateTransactionModel;
import com.capstone.state.Processors.SingleStateProcessor;
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
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Slf4j
public class BatchConfigSingleState {

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
    private SingleStateProcessor singleStateProcessor;

    @Autowired
    @Qualifier("writer_StateTransaction")
    private ClassifierCompositeItemWriter<StateTransactionModel> classifierCompositeItemWriter;

    @Autowired
    @Qualifier("taskExecutor_State")
    private org.springframework.core.task.TaskExecutor asyncTaskExecutor;

    @Autowired
    private StateTransactionClassifier stateTransactionClassifier;



    // ----------------------------------------------------------------------------------
    // --                             STEPS & JOBS                                     --
    // ----------------------------------------------------------------------------------

    // Step - single state transactions
    @Bean
    public Step step_exportSingleState() {

        return new StepBuilder("exportSingleStateStep", jobRepository)
                .<StateTransactionModel, StateTransactionModel> chunk(50000, transactionManager)
                .reader(synchronizedItemStreamReader)
                .processor(singleStateProcessor)
                .writer(classifierCompositeItemWriter)
                .listener(new StepExecutionListener() {
                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        stateTransactionClassifier.closeAllwriters();
                        log.info("------------------------------------------------------------------");
                        log.info(stepExecution.getSummary());
                        log.info("------------------------------------------------------------------");

                        singleStateProcessor.clearAllTrackersAndCounters();

                        return StepExecutionListener.super.afterStep(stepExecution);
                    }
                })
                .taskExecutor(asyncTaskExecutor)
                .build();
    }

    // Job - single state transactions
    @Bean
    public Job job_exportSingleState() {

        return new JobBuilder("exportSingleStateJob", jobRepository)
                .start(step_exportSingleState())
                .build();
    }
}
