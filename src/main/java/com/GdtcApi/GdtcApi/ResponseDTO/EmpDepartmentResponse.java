package com.GdtcApi.GdtcApi.ResponseDTO;

import com.GdtcApi.GdtcApi.Entities.EmpDepartment;
import lombok.Data;

@Data
public class EmpDepartmentResponse {

    public Integer departmentId;

    public String departmentName;


    public  static EmpDepartmentResponse MapToDto(EmpDepartment empDepartment)
    {
        EmpDepartmentResponse response = new EmpDepartmentResponse();
        response.departmentId = empDepartment.getDepartmentId();
        response.departmentName = empDepartment.getDepartmentName();

        return  response;

    }

}
