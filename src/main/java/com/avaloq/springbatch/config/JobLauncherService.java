package com.avaloq.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class JobLauncherService {

    @Autowired
    private JobLauncher jobLauncher;

    private Job job;
    private Job job2;

    @Autowired
    public JobLauncherService(JobLauncher jobLauncher,
                              @Qualifier("job") Job job,
                              @Qualifier("job2") Job job2) {
        this.jobLauncher = jobLauncher;
        this.job = job;
        this.job2 = job2;
    }


    public void launchJob() throws Exception {
        JobExecution jobExecution = jobLauncher.run(job, new JobParameters());
        System.out.println("Employee Job Status: " + jobExecution.getStatus());
    }

    public void launchJob2() throws Exception {
        JobExecution jobExecution = jobLauncher.run(job2, new JobParameters());
        System.out.println("Department Job Status: " + jobExecution.getStatus());
    }
}
