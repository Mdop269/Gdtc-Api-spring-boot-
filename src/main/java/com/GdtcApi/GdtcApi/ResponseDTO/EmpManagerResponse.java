package com.GdtcApi.GdtcApi.ResponseDTO;

import com.GdtcApi.GdtcApi.Entities.EmpLeaveType;
import com.GdtcApi.GdtcApi.Entities.Employee;
import lombok.Data;

@Data
public class EmpManagerResponse {

    public Integer managerId;

    public String firstName;

    public String lastName;

    public  static EmpManagerResponse MapToDto(Employee employee)
    {
        EmpManagerResponse response = new EmpManagerResponse();
        response.setManagerId(employee.getEmployeeId());
        response.setFirstName(employee.getFirstName());
        response.setLastName(employee.getLastName());

        return  response;

    }
}
