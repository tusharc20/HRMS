package com.avaloq.springbatch.config;

import com.avaloq.springbatch.entity.Employee;
import com.avaloq.springbatch.entity.PositionDto;
import org.aspectj.weaver.JoinPointSignatureIterator;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Collections;

@Configuration
public class CsvWriteBatchJobConfig {

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    @Bean(name = "job2")
    public Job exportJobBean(JobRepository jobRepository ,
                             JobCompletionNotificationImpl listener ,
                             Step step){
        return new JobBuilder("job2" , jobRepository)
                .listener(listener)
                .start(step)
                .build();
    }
    @Bean
    public Step step(JobRepository jobRepository ,
                     DataSourceTransactionManager transactionManager ,
                     ItemReader<Employee> itemReader ,
                     ItemWriter<PositionDto> itemWriter)
    {
        return new StepBuilder("jobStep2" , jobRepository)
                .<Employee , PositionDto>chunk(10, transactionManager)
                .reader(itemReader)
                .writer(itemWriter)
                .build();
    }

    //reader
    @Bean
    public JdbcPagingItemReader<PositionDto> itemReader(DataSource dataSource){
        return new JdbcPagingItemReaderBuilder<PositionDto>()
                .dataSource(dataSource)  // Injecting the DataSource
                .name("itemReader2")
                .selectClause("select employee_id, name, position")
                .fromClause("from employee_info")
                .sortKeys(Collections.singletonMap("employee_id", Order.ASCENDING)) // Sorting by employee_id
                .rowMapper(new BeanPropertyRowMapper<>(PositionDto.class))
                .pageSize(10)
                .build();
    }

    //writer
    @Bean
    public ItemWriter<PositionDto> itemWriter2(){
        return new FlatFileItemWriterBuilder<PositionDto>()
                .name("itemWriter2")
                .resource(new FileSystemResource("export.csv"))
                .delimited()
                .delimiter("-")
                .names(new String[]{"EmployeeID", "Name", "Position"})
                .build();
    }

}
