package com.capstone.state.Configurations;

import com.capstone.state.Models.StateModel;
import com.capstone.state.Processors.StateProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.batch.item.support.SynchronizedItemStreamWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Slf4j
public class BatchConfigState {

    // ----------------------------------------------------------------------------------
    // --                                  SETUP                                       --
    // ----------------------------------------------------------------------------------

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    @Qualifier("reader_State")
    private SynchronizedItemStreamReader<StateModel> synchronizedItemStreamReader;

    @Autowired
    private StateProcessor stateProcessor;

    @Autowired
    @Qualifier("writer_State")
    private SynchronizedItemStreamWriter<StateModel> xmlWriter;

    @Autowired
    private TaskExecutor taskExecutor;



    // ----------------------------------------------------------------------------------
    // --                             STEPS & JOBS                                     --
    // ----------------------------------------------------------------------------------

    // Step - card generation
    @Bean
    public Step step_generateStates() {

        return new StepBuilder("generateStatesStep", jobRepository)
                .<StateModel, StateModel> chunk(50000, transactionManager)
                .reader(synchronizedItemStreamReader)
                .processor(stateProcessor)
                .writer(xmlWriter)
                .listener(new StepExecutionListener() {
                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        log.info("------------------------------------------------------------------");
                        log.info(stepExecution.getSummary());
                        log.info("------------------------------------------------------------------");
                        return StepExecutionListener.super.afterStep(stepExecution);
                    }
                })
                .taskExecutor(taskExecutor)
                .build();
    }

    // Job - card generation
    @Bean
    public Job job_generateStates() {

        return new JobBuilder("generateStatesJob", jobRepository)
                .start(step_generateStates())
                .build();
    }
}
