package com.GdtcApi.GdtcApi.RequestDTO;

import com.GdtcApi.GdtcApi.Entities.EmpExpenseType;
import com.GdtcApi.GdtcApi.Entities.EmpLeaveType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmpLeaveTypeRequest {

    @NotBlank(message = "leaveTypeName is required")
    public String leaveTypeName;

    public static EmpLeaveType MapToEntity(EmpLeaveTypeRequest empLeaveTypeRequest)
    {
        EmpLeaveType entity = new EmpLeaveType();
        entity.leaveTypeName = empLeaveTypeRequest.leaveTypeName;

        return entity;
    }
}
