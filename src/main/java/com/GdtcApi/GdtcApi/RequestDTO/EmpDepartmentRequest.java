package com.GdtcApi.GdtcApi.RequestDTO;

import com.GdtcApi.GdtcApi.Entities.EmpDepartment;
import lombok.Data;

@Data
public class EmpDepartmentRequest {

    public String departmentName;

    public static EmpDepartment MapToEntity(EmpDepartmentRequest empDepartmentRequest)
    {
        EmpDepartment entity = new EmpDepartment();
        entity.departmentName = empDepartmentRequest.departmentName;

        return entity;
    }
}
