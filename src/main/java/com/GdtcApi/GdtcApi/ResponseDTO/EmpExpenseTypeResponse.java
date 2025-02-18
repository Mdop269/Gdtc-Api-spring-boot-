package com.GdtcApi.GdtcApi.ResponseDTO;

import com.GdtcApi.GdtcApi.Entities.EmpDepartment;
import com.GdtcApi.GdtcApi.Entities.EmpExpenseType;
import lombok.Data;

@Data
public class EmpExpenseTypeResponse {

    public Integer leaveTypeId;

    public String expenseTypeName;

    public  static EmpExpenseTypeResponse MapToDto(EmpExpenseType empExpenseType)
    {
        EmpExpenseTypeResponse response = new EmpExpenseTypeResponse();
        response.leaveTypeId = empExpenseType.getExpenseTypeId();
        response.expenseTypeName = empExpenseType.getExpenseTypeName();

        return  response;

    }
}
