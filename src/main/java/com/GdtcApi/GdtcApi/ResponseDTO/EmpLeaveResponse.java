package com.GdtcApi.GdtcApi.ResponseDTO;


import com.GdtcApi.GdtcApi.Entities.EmpLeave;
import com.GdtcApi.GdtcApi.Entities.EmpLeaveType;
import com.GdtcApi.GdtcApi.Entities.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmpLeaveResponse {

    public Integer leaveId;

    public LocalDateTime fromDate;

    public LocalDateTime toDate;

    public String note;

    public Boolean approved;

    public String status;

    public EmployeeResponse employeeResponse;
    
    public EmployeeResponse approverResponse;

    public EmpLeaveTypeResponse empLeaveTypeResponse;

    public static EmpLeaveResponse MapToDto(EmpLeave empLeave)
    {

        EmpLeaveResponse response = new EmpLeaveResponse();
        response.setLeaveId(empLeave.getLeaveId());
        response.setFromDate(empLeave.getFromDate());
        response.setToDate(empLeave.getToDate());
        response.setNote(empLeave.getNote());
        response.setApproved(empLeave.getApproved());
        response.setStatus(empLeave.getStatus());

        // Creating Employee reference with ID only
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setEmployeeId(empLeave.getEmployee().getEmployeeId());
        employeeResponse.setFirstName(empLeave.getEmployee().getFirstName());
        employeeResponse.setLastName(empLeave.getEmployee().getLastName());

        response.setEmployeeResponse(employeeResponse);

        // Creating approver reference with ID only
        EmployeeResponse approverResponse = new EmployeeResponse();
        approverResponse.setEmployeeId(empLeave.getApprover().getEmployeeId());
        approverResponse.setFirstName(empLeave.getApprover().getFirstName());
        approverResponse.setLastName(empLeave.getApprover().getLastName());


        response.setApproverResponse(approverResponse);

        // Creating EmpLeaveTypeResponse reference with ID only
        EmpLeaveTypeResponse empLeaveTypeResponse = new EmpLeaveTypeResponse();
        empLeaveTypeResponse.setLeaveTypeName(empLeave.getLeaveType().getLeaveTypeName());

        response.setEmpLeaveTypeResponse(empLeaveTypeResponse);


        return response;

    }
}
