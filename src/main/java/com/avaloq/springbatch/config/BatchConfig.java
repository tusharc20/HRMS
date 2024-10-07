package com.avaloq.springbatch.config;

import com.avaloq.springbatch.config.JobCompletionNotificationImpl;
import com.avaloq.springbatch.entity.Employee;
import com.avaloq.springbatch.reader.CustomItemProcessor;
import com.avaloq.springbatch.entity.Product;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfig {

    @Autowired
    private  DataSource dataSource;

    @Bean(name = "job")
    public Job jobBean(JobRepository jobRepository,
                       JobCompletionNotificationImpl listener,
                       Step steps
    ) {
        return new JobBuilder("job", jobRepository)
                .listener(listener)
                .start(steps)
                .build();
    }

    @Bean
    public Step steps(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<Employee> reader,
            ItemProcessor<Employee,Employee> processor,
            ItemWriter<Employee> writer
    ) {
        return new StepBuilder("jobStep", jobRepository)
                .<Employee,Employee>chunk(5, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    //    reader
    @Bean
    public FlatFileItemReader<Employee> reader() {
        return new FlatFileItemReaderBuilder<Employee>()
                .name("itemReader")
                .resource(new ClassPathResource("employee.csv"))
                .delimited()
                .names("EmployeeID", "Name", "Email", "Position")
                .targetType(Employee.class)
                .build();
    }

//    processor

    @Bean
    public ItemProcessor<Employee,Employee> itemProcessor() {
        return new CustomItemProcessor();
    }

//    writer

    @Bean
    public ItemWriter<Employee> itemWriter(DataSource dataSource) {

        return new JdbcBatchItemWriterBuilder<Employee>()
                .sql("insert into employee_info(EmployeeID,name,email,position)values(:EmployeeID, :name, :email, :position)")
                .dataSource(dataSource)
                .beanMapped()
                .build();

    }
}