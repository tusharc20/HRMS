package com.avaloq.springbatch.reader;

import com.avaloq.springbatch.entity.Employee;
import org.springframework.batch.item.ItemProcessor;


public class CustomItemProcessor implements ItemProcessor<Employee,Employee> {

    @Override
    public Employee process(Employee employee) throws Exception {
        //transform
        employee.setName(employee.getName().toUpperCase());
        return employee;
    }
}
