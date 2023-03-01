package com.capstone.state;

import com.capstone.state.Classifiers.StateTransactionClassifier;
import com.capstone.state.Configurations.BatchConfigTop5ZipCodes;
import com.capstone.state.Models.StateTransactionModel;
import com.capstone.state.PathHandlers.ReportsPathHandler;
import com.capstone.state.Processors.Top5ZipCodesProcessor;
import com.capstone.state.Readers.StateTransactionReaderCSV;
import com.capstone.state.TaskExecutors.TaskExecutor;
import com.capstone.state.Writers.StateTransactionCompositeWriter;
import com.capstone.state.Writers.StateWriterXML;
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

@SpringBatchTest
@SpringJUnitConfig(classes = {
        BatchConfigTop5ZipCodes.class,
        StateTransactionModel.class,
        StateTransactionReaderCSV.class,
        Top5ZipCodesProcessor.class,
        StateTransactionCompositeWriter.class,
        StateTransactionClassifier.class,
        TaskExecutor.class,
        ReportsPathHandler.class
})
@EnableAutoConfiguration

public class IntegrationTests_Top5Zips {

    // ----------------------------------------------------------------------------------
    // --                                  SETUP                                       --
    // ----------------------------------------------------------------------------------

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    private String INPUT = "src/test/resources/input/test_input2.csv";

    private String REPORTS_OUTPUT = "src/test/resources/output/top5_zipcodes_report";

    private String EXPECTED_OUTPUT = "src/test/resources/output/expected_output_top5Zipcodes";

    private String ACTUAL_OUTPUT = "src/test/resources/output/top5_zipcodes_report";

    @AfterEach
    public void cleanUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    private JobParameters testJobParameters_Top5ZipCodes() {

        return new JobParametersBuilder()
                .addString("file.input", INPUT)
                .addString("reportsPath_param", REPORTS_OUTPUT)
                .toJobParameters();
    }



    // ----------------------------------------------------------------------------------
    // --                                 TESTS                                        --
    // ----------------------------------------------------------------------------------

    @Test
    public void testBatchProcessFor_Top5ZipCodes() throws Exception {

        // Load job parameters and launch job through test suite
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(testJobParameters_Top5ZipCodes());
        JobInstance actualJobInstance = jobExecution.getJobInstance();
        ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

        // ----- Assertions -----
        File testInputFile = new File(INPUT);
        File testOutputFileExpected = new File(EXPECTED_OUTPUT);
        File testOutputFileActual = new File(ACTUAL_OUTPUT);

        // Match job names
        Assertions.assertEquals("exportTop5ZipCodesJob", actualJobInstance.getJobName());

        // Match job exit status to "COMPLETED"
        Assertions.assertEquals("COMPLETED", actualJobExitStatus.getExitCode());

        // Verify input file is valid and can be read
        Assertions.assertTrue(FileUtil.canReadFile(testInputFile));

        // Verify output (expected) file is valid and can be read
        Assertions.assertTrue(FileUtil.canReadFile(testOutputFileExpected));

        // Verify output (actual) file is valid and can be read
        Assertions.assertTrue(FileUtil.canReadFile(testOutputFileActual));

        // Verify expected and actual output files match
        Assertions.assertEquals(
                FileUtils.readFileToString(testOutputFileExpected, "utf-8"),
                FileUtils.readFileToString(testOutputFileActual, "utf-8"),
                "============================== FILE MISMATCH ==============================");
    }
}
