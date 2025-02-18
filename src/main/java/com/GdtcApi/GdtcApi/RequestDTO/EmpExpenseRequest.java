package com.GdtcApi.GdtcApi.RequestDTO;

import com.GdtcApi.GdtcApi.Entities.EmpDesignation;
import com.GdtcApi.GdtcApi.Entities.EmpExpense;
import com.GdtcApi.GdtcApi.Entities.EmpExpenseType;
import com.GdtcApi.GdtcApi.Entities.Employee;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EmpExpenseRequest {

    public LocalDateTime date;

    public BigDecimal cost;

    public String status;

    public Integer employeeId;

    public Integer approverId;

    public Integer expenseTypeId;

    public String comment;

    public static EmpExpense MapToEntity(EmpExpenseRequest empExpenseRequest, Employee employee, Employee approverEmployee, EmpExpenseType empExpenseType)
    {
        EmpExpense entity = new EmpExpense();
        entity.setDate(empExpenseRequest.getDate());
        entity.setCost(empExpenseRequest.getCost());
        entity.setStatus(empExpenseRequest.getStatus());
        entity.setComment(empExpenseRequest.getComment());

        entity.setEmployee(employee);
        entity.setApprover(approverEmployee);
        entity.setExpenseType(empExpenseType);

        return entity;
    }
}
