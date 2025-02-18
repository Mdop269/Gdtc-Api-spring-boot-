package com.GdtcApi.GdtcApi.Controller;

import com.GdtcApi.GdtcApi.RequestDTO.EmpDesignationRequest;
import com.GdtcApi.GdtcApi.RequestDTO.EmployeeRequest;
import com.GdtcApi.GdtcApi.ResponseDTO.EmpDesignationResponse;
import com.GdtcApi.GdtcApi.ResponseDTO.EmployeeResponse;
import com.GdtcApi.GdtcApi.Service.EmployeeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/employees")
@SecurityRequirement(name = "Bearer Authentication")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Create a new designation
    @PostMapping
    public CompletableFuture<ResponseEntity<EmployeeResponse>> createEmployee(
            @Valid @RequestBody EmployeeRequest employeeRequest) {
        return employeeService.upsertEmployeeAsync(employeeRequest)
                .thenApply(savedEmployee ->
                        new ResponseEntity<>(savedEmployee, HttpStatus.CREATED)
                )
                .exceptionally(ex -> {
                    // Handle exceptions and return an appropriate error response
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }
}
