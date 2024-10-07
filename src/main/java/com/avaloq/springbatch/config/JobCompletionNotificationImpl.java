package com.avaloq.springbatch.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component @Configuration @Slf4j
public class JobCompletionNotificationImpl implements JobExecutionListener {

    @Override
    public void afterJob(JobExecution jobExecution) {
        
}
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.error("job started");
    }
}
