package com.capstone.state.Services;

import com.capstone.state.Configurations.BatchConfigState;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.NoSuchElementException;

@Service
public class StateService {

    // ----------------------------------------------------------------------------------
    // --                                  SETUP                                       --
    // ----------------------------------------------------------------------------------

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    BatchConfigState batchConfigState;

    private JobParameters buildJobParameters_State(String pathInput, String pathOutput) {

        // Check if source file.input is valid
        File file = new File(pathInput);
        if (!file.exists()) {
            throw new ItemStreamException("Requested source doesn't exist");
        }

        return new JobParametersBuilder()
                .addLong("time.Started", System.currentTimeMillis())
                .addString("file.input", pathInput)
                .addString("outputPath_param", pathOutput)
                .toJobParameters();
    }



    // ----------------------------------------------------------------------------------
    // --                                METHODS                                       --
    // ----------------------------------------------------------------------------------

    // generate card numbers
    public ResponseEntity<String> generateStates(String pathInput, String pathOutput) {

        try {
            JobParameters jobParameters = buildJobParameters_State(pathInput, pathOutput);
            jobLauncher.run(batchConfigState.job_generateStates(), jobParameters);

        } catch (BeanCreationException e) {
            return new ResponseEntity<>("Bean creation had an error. Job halted.", HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Requested source doesn't exist", HttpStatus.BAD_REQUEST);
        } catch (JobExecutionAlreadyRunningException e) {
            return new ResponseEntity<>("Job execution already running", HttpStatus.BAD_REQUEST);
        } catch (JobRestartException e) {
            return new ResponseEntity<>("Job restart exception", HttpStatus.BAD_REQUEST);
        } catch (JobInstanceAlreadyCompleteException e) {
            return new ResponseEntity<>("Job already completed", HttpStatus.BAD_REQUEST);
        } catch (JobParametersInvalidException e) {
            return new ResponseEntity<>("Job parameters are invalid", HttpStatus.BAD_REQUEST);
        }

        // Job successfully ran
        return new ResponseEntity<>("Job parameters OK. Job Completed", HttpStatus.CREATED);
    }
}
