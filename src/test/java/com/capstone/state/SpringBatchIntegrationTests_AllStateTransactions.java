package com.capstone.state;

import com.capstone.state.Classifiers.StateTransactionClassifier;
import com.capstone.state.Configurations.BatchConfigAllStates;
import com.capstone.state.Models.StateTransactionModel;
import com.capstone.state.Processors.AllStatesProcessor;
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
//                          Test All States Operations
// ********************************************************************************

@SpringBatchTest
@SpringJUnitConfig(classes = {
        BatchConfigAllStates.class,
        StateTransactionClassifier.class,
        StateTransactionModel.class,
        StateTransactionReaderCSV.class,
        AllStatesProcessor.class,
        StateTransactionCompositeWriter.class,
        TaskExecutor.class
})
@EnableAutoConfiguration

public class SpringBatchIntegrationTests_AllStateTransactions {

    // ----------------------------------------------------------------------------------
    // --                                  SETUP                                       --
    // ----------------------------------------------------------------------------------

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    // Hardcoded stateID - matches first (and only) stateID in test_input.csv source
    private String stateID_first = "CA";

    // Hardcoded stateID - matches second (blank) stateID in test_input.csv source
    private String stateID_second = "";

    private String INPUT = "src/test/resources/input/test_input.csv";
    private String EXPECTED_OUTPUT_1 = "src/test/resources/output/expected_output_AllStatesTransaction_1.xml";

    private String EXPECTED_OUTPUT_2 = "src/test/resources/output/expected_output_AllStatesTransaction_2.xml";
    private String ACTUAL_OUTPUT = "src/test/resources/output/states";

    @AfterEach
    public void cleanUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    private JobParameters testJobParameters_AllStatesTransaction() {

        return new JobParametersBuilder()
                .addString("file.input", INPUT)
                .addString("outputPath_param", ACTUAL_OUTPUT)
                .toJobParameters();
    }



    // ----------------------------------------------------------------------------------
    // --                                 TESTS                                        --
    // ----------------------------------------------------------------------------------

    @Test
    public void testBatchProcessFor_AllStatesTransaction() throws Exception {

        // Load job parameters and launch job through test suite
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(testJobParameters_AllStatesTransaction());
        JobInstance actualJobInstance = jobExecution.getJobInstance();
        ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

        // ----- Assertions -----
        File testInputFile = new File(INPUT);
        File testOutputFileExpected_1 = new File(EXPECTED_OUTPUT_1);
        File testOutputFileExpected_2 = new File(EXPECTED_OUTPUT_2);
        File testOutputFileActual_1 = new File(ACTUAL_OUTPUT + "/state_" + stateID_first + "_transactions.xml");
        File testOutputFileActual_2 = new File(ACTUAL_OUTPUT + "/state_" + stateID_second + "_transactions.xml");

        // Match job names
        Assertions.assertEquals("exportAllStatesJob", actualJobInstance.getJobName());

        // Match job exit status to "COMPLETED"
        Assertions.assertEquals("COMPLETED", actualJobExitStatus.getExitCode());

        // Verify input file is valid and can be read
        Assertions.assertTrue(FileUtil.canReadFile(testInputFile));

        // Verify output (expected) file 1 is valid and can be read
        Assertions.assertTrue(FileUtil.canReadFile(testOutputFileExpected_1));

        // Verify output (expected) file 2 is valid and can be read
        Assertions.assertTrue(FileUtil.canReadFile(testOutputFileExpected_2));

        // Verify output (actual) file 1 is valid and can be read
        Assertions.assertTrue(FileUtil.canReadFile(testOutputFileActual_1));

        // Verify output (actual) file 2 is valid and can be read
        Assertions.assertTrue(FileUtil.canReadFile(testOutputFileActual_2));

        // Verify expected and actual output files match (file _1)
        Assertions.assertEquals(
                FileUtils.readFileToString(testOutputFileExpected_1, "utf-8"),
                FileUtils.readFileToString(testOutputFileActual_1, "utf-8"),
                "============================== FILE MISMATCH ==============================");

        // Verify expected and actual output files match (file _2)
        Assertions.assertEquals(
                FileUtils.readFileToString(testOutputFileExpected_2, "utf-8"),
                FileUtils.readFileToString(testOutputFileActual_2, "utf-8"),
                "============================== FILE MISMATCH ==============================");
    }
}
