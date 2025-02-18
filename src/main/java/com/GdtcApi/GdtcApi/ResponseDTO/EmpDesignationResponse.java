package com.GdtcApi.GdtcApi.ResponseDTO;

import com.GdtcApi.GdtcApi.Entities.EmpDepartment;
import com.GdtcApi.GdtcApi.Entities.EmpDesignation;
import com.GdtcApi.GdtcApi.RequestDTO.EmpDesignationRequest;
import lombok.Data;

@Data
public class EmpDesignationResponse {

    public Integer designationId;

    public String designationTitle;

    public EmpDepartmentResponse empDepartmentResponse;

    public static EmpDesignationResponse MapToDto(EmpDesignation empDesignation)
    {

        EmpDesignationResponse response = new EmpDesignationResponse();
        response.setDesignationId(empDesignation.getDesignationId());
        response.setDesignationTitle(empDesignation.getDesignationTitle());

        // Creating department reference with ID only
        EmpDepartmentResponse department = new EmpDepartmentResponse();
        department.setDepartmentId(empDesignation.getDepartment().getDepartmentId());
        department.setDepartmentName(empDesignation.getDepartment().getDepartmentName());
        response.setEmpDepartmentResponse(department);

        return response;

    }
}
