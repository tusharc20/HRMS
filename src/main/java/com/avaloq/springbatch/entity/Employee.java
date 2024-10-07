package com.avaloq.springbatch.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employee_info")
@Getter @Setter @AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @Column(name = "EmployeeID")
    private String employeeID;
    @Column(name = "email")
    private String email;
    @Column(name = "name")
    private String name;
    @Column(name = "position")
    private String position;

}
