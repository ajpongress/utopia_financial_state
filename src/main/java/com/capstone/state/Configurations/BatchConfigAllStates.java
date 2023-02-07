package com.capstone.state.Configurations;

import com.capstone.state.Classifiers.StateTransactionClassifier;
import com.capstone.state.Models.StateTransactionModel;
import com.capstone.state.Processors.AllStatesProcessor;
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
public class BatchConfigAllStates {

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
    private AllStatesProcessor allStatesProcessor;

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

    // Step - all states transactions
    @Bean
    public Step step_exportAllStates() {

        return new StepBuilder("exportAllStatesStep", jobRepository)
                .<StateTransactionModel, StateTransactionModel> chunk(50000, transactionManager)
                .reader(synchronizedItemStreamReader)
                .processor(allStatesProcessor)
                .writer(classifierCompositeItemWriter)
                .listener(new StepExecutionListener() {
                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        stateTransactionClassifier.closeAllwriters();
                        log.info("------------------------------------------------------------------");
                        log.info(stepExecution.getSummary());
                        log.info("------------------------------------------------------------------");
                        return StepExecutionListener.super.afterStep(stepExecution);
                    }
                })
                .taskExecutor(asyncTaskExecutor)
                .build();
    }

    // Job - all states transactions
    @Bean
    public Job job_exportAllStates() {

        return new JobBuilder("exportAllStatesJob", jobRepository)
                .start(step_exportAllStates())
                .build();
    }
}
