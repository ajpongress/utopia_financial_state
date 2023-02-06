package com.capstone.state;

// ********************************************************************************
//                          Test Single State Transaction Operations
// ********************************************************************************

import com.capstone.state.Classifiers.StateTransactionClassifier;
import com.capstone.state.Configurations.BatchConfigSingleState;
import com.capstone.state.Models.StateTransactionModel;
import com.capstone.state.Processors.SingleStateProcessor;
import com.capstone.state.Readers.StateTransactionReaderCSV;
import com.capstone.state.TaskExecutors.TaskExecutor;
import com.capstone.state.Writers.StateTransactionCompositeWriter;
import org.apache.commons.io.FileUtils;
import org.aspectj.util.FileUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.File;

// ********************************************************************************
//                          Test Single State Operations
// ********************************************************************************

@SpringBatchTest
@SpringJUnitConfig(classes = {
        BatchConfigSingleState.class,
        StateTransactionClassifier.class,
        StateTransactionModel.class,
        StateTransactionReaderCSV.class,
        SingleStateProcessor.class,
        StateTransactionCompositeWriter.class,
        TaskExecutor.class
})
@EnableAutoConfiguration

public class SpringBatchIntegrationTests_SingleStateTransaction {

    // ----------------------------------------------------------------------------------
    // --                                  SETUP                                       --
    // ----------------------------------------------------------------------------------

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    // Set stateID to test for single state operations & export
    private String stateID = "CA";
    private String INPUT = "src/test/resources/input/test_input.csv";
    private String EXPECTED_OUTPUT = "src/test/resources/output/expected_output_SingleStateTransaction.xml";
    private String ACTUAL_OUTPUT = "src/test/resources/output/state_" + stateID;

    @AfterEach
    public void cleanUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    private JobParameters testJobParameters_SingleStateTransaction() {

        return new JobParametersBuilder()
                .addString("stateID_param", stateID)
                .addString("file.input", INPUT)
                .addString("outputPath_param", ACTUAL_OUTPUT)
                .toJobParameters();
    }



    // ----------------------------------------------------------------------------------
    // --                                 TESTS                                        --
    // ----------------------------------------------------------------------------------

    @Test
    public void testBatchProcessFor_SingleStateTransaction() throws Exception {

        // Load job parameters and launch job through test suite
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(testJobParameters_SingleStateTransaction());
        JobInstance actualJobInstance = jobExecution.getJobInstance();
        ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

        // ----- Assertions -----
        File testInputFile = new File(INPUT);
        File testOutputFileExpected = new File(EXPECTED_OUTPUT);
        File testOutputFileActual = new File(ACTUAL_OUTPUT + "/state_" + stateID + "_transactions.xml");

        // Match job names
        Assertions.assertEquals("exportSingleStateJob", actualJobInstance.getJobName());
        // Match job exit status to "COMPLETED"
        Assertions.assertEquals("COMPLETED", actualJobExitStatus.getExitCode());
        // Verify input file is valid and can be read
        Assertions.assertTrue(FileUtil.canReadFile(testOutputFileExpected));
        // Verify output (expected) file is valid and can be read
        Assertions.assertTrue(FileUtil.canReadFile(testInputFile));
        // Verify output (actual) file is valid and can be read
        Assertions.assertTrue(FileUtil.canReadFile(testOutputFileActual));

        // Verify expected and actual output files match
        Assertions.assertEquals(
                FileUtils.readFileToString(testOutputFileExpected, "utf-8"),
                FileUtils.readFileToString(testOutputFileActual, "utf-8"),
                "============================== FILE MISMATCH ==============================");
    }
}
