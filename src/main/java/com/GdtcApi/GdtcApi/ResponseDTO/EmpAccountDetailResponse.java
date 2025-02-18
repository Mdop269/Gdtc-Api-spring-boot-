package com.GdtcApi.GdtcApi.ResponseDTO;

import com.GdtcApi.GdtcApi.Entities.EmpAccountDetail;
import com.GdtcApi.GdtcApi.Entities.EmpDesignation;
import lombok.Data;

@Data
public class EmpAccountDetailResponse {

    public Integer accountDetailId;

    public String bankName;

    public String ifciCode;

    public String branch;

    public String nameOnAccount;

    public String accountNumber;

    public EmployeeResponse employeeResponse;

    public static EmpAccountDetailResponse MapToDto(EmpAccountDetail empAccountDetail)
    {

        EmpAccountDetailResponse response = new EmpAccountDetailResponse();
        response.setAccountDetailId(empAccountDetail.getAccountDetailId());
        response.setBankName(empAccountDetail.getBankName());
        response.setIfciCode(empAccountDetail.getIfciCode());
        response.setBranch(empAccountDetail.getBranch());
        response.setNameOnAccount(empAccountDetail.getNameOnAccount());
        response.setAccountNumber(empAccountDetail.getAccountNumber());



//         Creating EmployeeResponse reference with ID only
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setEmployeeId(empAccountDetail.getEmployee().getEmployeeId());
        employeeResponse.setFirstName(empAccountDetail.getEmployee().getFirstName());
        employeeResponse.setLastName(empAccountDetail.getEmployee().getLastName());

        response.setEmployeeResponse(employeeResponse);

        return response;

    }


}
