package com.omer.spring.batch.controller;

import com.omer.spring.batch.entity.AbcAnalysisCloud;
import com.omer.spring.batch.repository.AbcAnalysisRepository;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class BatchJobController {


    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Autowired
    private AbcAnalysisRepository repository;
    @Autowired
    private JobRepository jobRepository;

    private final String TEMP_STORAGE = "C:/Users/Omer Sabir/Desktop/batch-files/";

    @PostMapping(path = "/importData")
    public void startBatch(@RequestParam("file") MultipartFile multipartFile) {


        // file  -> path we don't know
        //copy the file to some storage in your VM : get the file path
        //copy the file to DB : get the file path

        try {
            String originalFileName = multipartFile.getOriginalFilename();
            File fileToImport = new File(TEMP_STORAGE + originalFileName);
            multipartFile.transferTo(fileToImport);

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("fullPathFileName", TEMP_STORAGE + originalFileName)
                    .addLong("startAt", System.currentTimeMillis()).toJobParameters();

            JobExecution execution = jobLauncher.run(job, jobParameters);

            if(execution.getExitStatus().getExitCode().equals(ExitStatus.COMPLETED)){
                //delete the file from the TEMP_STORAGE
                Files.deleteIfExists(Paths.get(TEMP_STORAGE + originalFileName));
            }

        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException | IOException e) {

            e.printStackTrace();
        }
    }

    @GetMapping("/allData")
    public List<AbcAnalysisCloud> getAll() {
        return repository.findAll();
    }
}
