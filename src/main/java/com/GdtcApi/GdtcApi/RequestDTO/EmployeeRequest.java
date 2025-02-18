package com.GdtcApi.GdtcApi.RequestDTO;

import com.GdtcApi.GdtcApi.Entities.EmpAccountDetail;
import com.GdtcApi.GdtcApi.Entities.EmpDesignation;
import com.GdtcApi.GdtcApi.Entities.Employee;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EmployeeRequest {

    public String firstName;

    public String lastName;

    public LocalDateTime birthDate;

    public Boolean probation;

    public BigDecimal salary;

    public LocalDateTime hireDate;

    public Integer managerId;

    public Integer designationId;

    public static Employee MapToEntity(EmployeeRequest employeeRequest, Employee managerEmployee, EmpDesignation empDesignation)
    {
        Employee entity = new Employee();
        entity.setFirstName(employeeRequest.getFirstName());
        entity.setLastName(employeeRequest.getLastName());
        entity.setBirthDate(employeeRequest.getBirthDate());
        entity.setProbation(employeeRequest.getProbation());
        entity.setSalary(employeeRequest.getSalary());
        entity.setHireDate(employeeRequest.getHireDate());

        entity.setManager(managerEmployee);
        entity.setDesignation(empDesignation);

        return entity;
    }
}
