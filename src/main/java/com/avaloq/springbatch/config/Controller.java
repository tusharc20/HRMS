package com.avaloq.springbatch.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private JobLauncherService jobLauncherService;

    @GetMapping("/job1")
    public String writeinDB() throws Exception {
        jobLauncherService.launchJob();
        return "job success";
    }

    @GetMapping("/job2")
    public  String exportCsv() throws  Exception{
        jobLauncherService.launchJob2();
        return "csv file job sucesss";
    }
}
