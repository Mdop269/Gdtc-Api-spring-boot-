package com.GdtcApi.GdtcApi.SecuirtyModels;

import com.GdtcApi.GdtcApi.RequestDTO.UsersRequest;
import com.GdtcApi.GdtcApi.Service.SecurityService.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UsersDataIntializer {

    @Autowired
    private UsersService usersService;

//    @EventListener(ApplicationReadyEvent.class)
//    public void init() {
//        List<Users> users = new ArrayList<>(List.of(
//                new Users("mdop1", "mdop1"),
//                new Users("mdop2", "mdop2")
//        ));
//
//
//        usersService.AddDefaultList(users);
//    }


    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        String json = "{ \"userName\": \"string\", \"password\": \"string\" }";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            UsersRequest userRequest = objectMapper.readValue(json, UsersRequest.class);
            usersService.upsertUsers(userRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
