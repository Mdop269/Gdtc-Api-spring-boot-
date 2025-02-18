package com.GdtcApi.GdtcApi.RequestDTO;

import com.GdtcApi.GdtcApi.Entities.Users;
import lombok.Data;

@Data
public class UsersRequest {

    public String userName;

    public String password;



    public static Users MapToEntity(UsersRequest usersRequest)
    {
        Users entity = new Users();
        entity.setUserName(usersRequest.getUserName());
        entity.setPassword(usersRequest.getPassword());

        return entity;
    }
}
