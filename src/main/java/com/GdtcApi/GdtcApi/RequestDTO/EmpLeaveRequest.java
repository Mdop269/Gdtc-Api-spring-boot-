package com.GdtcApi.GdtcApi.RequestDTO;

import com.GdtcApi.GdtcApi.Entities.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmpLeaveRequest {

    public LocalDateTime fromDate;

    public LocalDateTime toDate;

    public String note;

    public Boolean approved;

    public String status;

    public Integer employeeId;

    public Integer approverId;

    public Integer leaveTypeId;

    public static EmpLeave MapToEntity(EmpLeaveRequest empLeaveRequest, Employee employee, Employee approverEmployee, EmpLeaveType empLeaveType)
    {
        EmpLeave entity = new EmpLeave();
        entity.setFromDate(empLeaveRequest.getFromDate());
        entity.setToDate(empLeaveRequest.getToDate());
        entity.setNote(empLeaveRequest.getNote());
        entity.setApproved(empLeaveRequest.getApproved());
        entity.setStatus(empLeaveRequest.getStatus());

        entity.setEmployee(employee);
        entity.setApprover(approverEmployee);
        entity.setLeaveType(empLeaveType);

        return entity;
    }



}
