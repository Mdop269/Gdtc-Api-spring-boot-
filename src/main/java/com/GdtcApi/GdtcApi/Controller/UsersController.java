package com.GdtcApi.GdtcApi.Controller;

import com.GdtcApi.GdtcApi.RequestDTO.UsersRequest;
import com.GdtcApi.GdtcApi.ResponseDTO.UsersResponse;
import com.GdtcApi.GdtcApi.Service.SecurityService.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class UsersController {

    @Autowired
    UsersService usersService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

//    @PostMapping("/register")
//    public UsersResponse register( @Valid @RequestBody UsersRequest usersRequest)
//    {
//        System.out.println("Received user: " + usersRequest);
//        usersRequest.setPassword(encoder.encode(usersRequest.getPassword()));
//        return usersService.registerUser(usersRequest);
//    }

    @PostMapping("/register") // wont need for authentication
    public CompletableFuture<ResponseEntity<UsersResponse>> createUsers(
            @Valid @RequestBody UsersRequest usersRequest) {
        usersRequest.setPassword(encoder.encode(usersRequest.getPassword())); // in this we are encoding the password
        return usersService.upsertUsersAsync(usersRequest)
                .thenApply(savedUser ->
                        new ResponseEntity<>(savedUser, HttpStatus.CREATED)
                )
                .exceptionally(ex -> {
                    // Handle exceptions and return an appropriate error response
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }


    @PostMapping("/login")
    public  String login(@RequestBody UsersRequest usersRequest){

        return usersService.verify(usersRequest);
    }

}
