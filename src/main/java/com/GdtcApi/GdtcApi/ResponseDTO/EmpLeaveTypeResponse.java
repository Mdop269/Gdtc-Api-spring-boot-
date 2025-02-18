package com.GdtcApi.GdtcApi.ResponseDTO;

import com.GdtcApi.GdtcApi.Entities.EmpExpenseType;
import com.GdtcApi.GdtcApi.Entities.EmpLeaveType;
import lombok.Data;

@Data
public class EmpLeaveTypeResponse {

    public Integer leaveTypeId;

    public String leaveTypeName;

    public  static EmpLeaveTypeResponse MapToDto(EmpLeaveType empLeaveType)
    {
        EmpLeaveTypeResponse response = new EmpLeaveTypeResponse();
        response.leaveTypeId = empLeaveType.getLeaveTypeId();
        response.leaveTypeName = empLeaveType.getLeaveTypeName();

        return  response;

    }
}
