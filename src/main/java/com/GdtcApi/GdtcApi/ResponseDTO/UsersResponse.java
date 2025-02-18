package com.GdtcApi.GdtcApi.ResponseDTO;

import com.GdtcApi.GdtcApi.Entities.Users;
import lombok.Data;

@Data
public class UsersResponse {

    public Integer userId;

    public String userName;

    public String password;

    public  static UsersResponse MapToDto(Users users)
    {
        UsersResponse response = new UsersResponse();
        response.setUserId(users.getUserId());
        response.setUserName(users.getUserName());
        response.setPassword(users.getPassword());

        return  response;

    }
}
