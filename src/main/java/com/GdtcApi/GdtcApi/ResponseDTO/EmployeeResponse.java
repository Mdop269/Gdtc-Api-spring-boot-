package com.GdtcApi.GdtcApi.ResponseDTO;

import com.GdtcApi.GdtcApi.Entities.EmpDesignation;
import com.GdtcApi.GdtcApi.Entities.Employee;
import jakarta.persistence.Column;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EmployeeResponse {

    public Integer employeeId;

    public String firstName;

    public String lastName;

    public LocalDateTime birthDate;

    public Boolean probation;

    public BigDecimal salary;

    public LocalDateTime hireDate;

    public EmpManagerResponse managerEmployee;

    public EmpDesignationResponse empDesignationResponse;

    public EmpDepartmentResponse empDepartmentResponse;


    public static EmployeeResponse MapToDto(Employee employee)
    {

        EmployeeResponse response = new EmployeeResponse();
        response.setEmployeeId(employee.getEmployeeId());
        response.setFirstName(employee.getFirstName());
        response.setLastName(employee.getLastName());
        response.setBirthDate(employee.getBirthDate());
        response.setProbation(employee.getProbation());
        response.setSalary(employee.getSalary());
        response.setHireDate(employee.getHireDate());

        // Creating manager reference with ID only
        EmpManagerResponse manager = new EmpManagerResponse();
        manager.setManagerId(employee.getManager().getEmployeeId());
        manager.setFirstName(employee.getManager().getFirstName());
        manager.setLastName(employee.getManager().getLastName());

        response.setManagerEmployee(manager);

        // Creating Designation reference with ID only
        EmpDesignationResponse designationResponse = new EmpDesignationResponse();
        designationResponse.setDesignationId(employee.getDesignation().getDesignationId());
        designationResponse.setDesignationTitle(employee.getDesignation().getDesignationTitle());



        response.setEmpDesignationResponse(designationResponse);

        // Creating department reference with ID only
        EmpDepartmentResponse department = new EmpDepartmentResponse();
        department.setDepartmentId(employee.getDesignation().getDepartment().getDepartmentId());
        department.setDepartmentName(employee.getDesignation().getDepartment().getDepartmentName());
        response.setEmpDepartmentResponse(department);


        return response;

    }
}
