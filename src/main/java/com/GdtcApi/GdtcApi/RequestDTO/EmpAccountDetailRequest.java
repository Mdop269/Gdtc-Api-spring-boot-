package com.GdtcApi.GdtcApi.RequestDTO;

import com.GdtcApi.GdtcApi.Entities.EmpAccountDetail;
import com.GdtcApi.GdtcApi.Entities.EmpDepartment;
import com.GdtcApi.GdtcApi.Entities.EmpDesignation;
import com.GdtcApi.GdtcApi.Entities.Employee;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmpAccountDetailRequest {

    @NotBlank(message = "bankName is required")
    public String bankName;

    @NotBlank(message = "ifciCode is required")
    public String ifciCode;

    @NotBlank(message = "branch is required")
    public String branch;

    @NotBlank(message = "nameOnAccount is required")
    public String nameOnAccount;

    @NotBlank(message = "accountNumber is required")
    public String accountNumber;

    @NotBlank(message = "employeeId is required")
    public Integer employeeId;

    public static EmpAccountDetail MapToEntity(EmpAccountDetailRequest empAccountDetailRequest, Employee employee)
    {
        EmpAccountDetail entity = new EmpAccountDetail();
        entity.setBankName(empAccountDetailRequest.getBankName());
        entity.setIfciCode(empAccountDetailRequest.getIfciCode());
        entity.setBranch(empAccountDetailRequest.getBranch());
        entity.setNameOnAccount(empAccountDetailRequest.getNameOnAccount());
        entity.setAccountNumber(empAccountDetailRequest.getAccountNumber());
        entity.setEmployee(employee);

        return entity;
    }
}
