package com.GdtcApi.GdtcApi.ResponseDTO;

import com.GdtcApi.GdtcApi.Entities.EmpExpense;
import com.GdtcApi.GdtcApi.Entities.EmpExpenseType;
import com.GdtcApi.GdtcApi.Entities.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EmpExpenseResponse {

    public Integer expenseId;

    public LocalDateTime date;

    public BigDecimal cost;

    public String status;

    public EmployeeResponse employeeResponse;

    public EmployeeResponse approverResponse;

    public EmpExpenseTypeResponse expenseTypeResponse;

    public String comment;

    public static EmpExpenseResponse MapToDto(EmpExpense empExpense)
    {

        EmpExpenseResponse response = new EmpExpenseResponse();
        response.setExpenseId(empExpense.getExpenseId());
        response.setDate(empExpense.getDate());
        response.setCost(empExpense.getCost());
        response.setStatus(empExpense.getStatus());

        // Creating Employee reference with ID only
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setEmployeeId(empExpense.getEmployee().getEmployeeId());
        employeeResponse.setFirstName(empExpense.getEmployee().getFirstName());
        employeeResponse.setLastName(empExpense.getEmployee().getLastName());

        response.setEmployeeResponse(employeeResponse);

        // Creating approver reference with ID only
        EmployeeResponse approverResponse = new EmployeeResponse();
        approverResponse.setEmployeeId(empExpense.getApprover().getEmployeeId());
        approverResponse.setFirstName(empExpense.getApprover().getFirstName());
        approverResponse.setLastName(empExpense.getApprover().getLastName());


        response.setApproverResponse(approverResponse);

        // Creating department reference with ID only
        EmpExpenseTypeResponse empExpenseTypeResponse = new EmpExpenseTypeResponse();
        empExpenseTypeResponse.setExpenseTypeName(empExpense.getExpenseType().getExpenseTypeName());
        response.setExpenseTypeResponse(empExpenseTypeResponse);


        return response;

    }
}
