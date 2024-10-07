package com.avaloq.springbatch.config;

import com.avaloq.springbatch.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BatchConfigTest {

    @Autowired
    private FlatFileItemReader<Employee> reader;

    @Test
    public void testFileReading() throws Exception {
        Employee employee;
        reader.open(new ExecutionContext());
        while ((employee = reader.read()) != null) {
            System.out.println("Read employee: " + employee);
        }
    }
}x
