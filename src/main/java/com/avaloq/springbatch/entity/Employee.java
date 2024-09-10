package com.avaloq.springbatch.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employee_info")
@Getter @Setter @AllArgsConstructor
public class Employee {

    @Column(name = "employee_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeId;
    @Column(name = "email")
    private String email;
    @Column(name = "emp_name")
    private String empName;
    @Column(name = "position")
    private String position;

}
