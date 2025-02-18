package com.GdtcApi.GdtcApi.RequestDTO;

import com.GdtcApi.GdtcApi.Entities.EmpDepartment;
import com.GdtcApi.GdtcApi.Entities.EmpExpenseType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmpExpenseTypeRequest {

    @NotBlank(message = "expenseTypeName is required")
    public String expenseTypeName;

    public static EmpExpenseType MapToEntity(EmpExpenseTypeRequest empExpenseTypeRequest)
    {
        EmpExpenseType entity = new EmpExpenseType();
        entity.expenseTypeName = empExpenseTypeRequest.expenseTypeName;

        return entity;
    }
}
