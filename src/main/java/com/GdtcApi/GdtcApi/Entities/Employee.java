package com.GdtcApi.GdtcApi.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "emp_employee")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    public Integer employeeId;

    @Size(max = 50)// Bean Validation for application-level checks
    @Column(name = "first_name", nullable = false , length = 50)
    public String firstName;

    @Size(max = 50)// Bean Validation for application-level checks
    @Column(name = "last_name", nullable = false , length = 50)
    public String lastName;

    @Column(name = "birth_date", nullable = false)
    public LocalDateTime birthDate;

//    "tinyint(1) default 0" for my sql use this
    @Column(name = "approved",columnDefinition = "BOOLEAN default false")
    public Boolean probation;

    @Column(name = "salary",nullable = false, scale = 2)
    public BigDecimal salary;

    @Column(name = "hireDate" ,nullable = false)
    public LocalDateTime hireDate;

    @ManyToOne
    @JoinColumn(name = "ManagerId",nullable = false, foreignKey = @ForeignKey(name = "Employee_fk_2"))
    public Employee manager;

    @ManyToOne
    @JoinColumn(name = "DesignationId", nullable = false, foreignKey = @ForeignKey(name = "Employee_fk_1"))
    public EmpDesignation designation;

    @OneToMany(mappedBy = "manager")
    public List<Employee> subordinates;

    @OneToMany(mappedBy = "employee")
    public List<EmpAccountDetail> accountDetails;

    @OneToMany(mappedBy = "employee")
    public List<EmpExpense> expenses;

    @OneToMany(mappedBy = "approver")
    public List<EmpExpense> approvedExpenses;

    @OneToMany(mappedBy = "employee")
    public List<EmpLeave> leaves;

    @OneToMany(mappedBy = "approver")
    public List<EmpLeave> approvedLeaves;

}
