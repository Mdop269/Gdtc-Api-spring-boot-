package com.GdtcApi.GdtcApi.Controller;

import com.GdtcApi.GdtcApi.SecuirtyModels.StudentTest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class SecurityController {

//    the  below is all for default Spring Security we will change now in configuration
    private List<StudentTest> studentTest = new ArrayList<>(List.of(
            new StudentTest(1,"mdop","mdop"),
            new StudentTest(2,"kasa","aahesh")
    ));

    @GetMapping("/")
    public String greet(HttpServletRequest request){
        return "hi" + request.getSession().getId();
    }

//    we need csrf token for post put delete we cant do without that
    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request)
    {
        return (CsrfToken) request.getAttribute("_csrf"); //this attribute is in html of the spring login
    }


    @GetMapping("/students")
    public List<StudentTest> GetAllUsers(){
        return studentTest;
    }

    // we cant send now without the header of crf token then only it will work
    @PostMapping("/students")
    public StudentTest addUser(@RequestBody StudentTest student){
        studentTest.add(student);
        return student;
    }


}
