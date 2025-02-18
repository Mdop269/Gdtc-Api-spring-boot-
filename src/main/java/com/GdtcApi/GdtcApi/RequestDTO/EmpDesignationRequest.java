package com.GdtcApi.GdtcApi.RequestDTO;

import com.GdtcApi.GdtcApi.Entities.EmpDepartment;
import com.GdtcApi.GdtcApi.Entities.EmpDesignation;
import com.GdtcApi.GdtcApi.ResponseDTO.EmpDepartmentResponse;
import com.GdtcApi.GdtcApi.ResponseDTO.EmpDesignationResponse;
import com.GdtcApi.GdtcApi.Service.EmpDesignationService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmpDesignationRequest {

    @NotBlank(message = "Designation title is required")
    public String designationTitle;

    @NotNull(message = "Department ID is required")
    public Integer departmentId;

    public static EmpDesignation MapToEntity(EmpDesignationRequest empDesignationRequest, EmpDepartment empDepartment)
    {
        EmpDesignation entity = new EmpDesignation();
        entity.setDesignationTitle(empDesignationRequest.getDesignationTitle());
        entity.setDepartment(empDepartment);

        return entity;
    }
}
