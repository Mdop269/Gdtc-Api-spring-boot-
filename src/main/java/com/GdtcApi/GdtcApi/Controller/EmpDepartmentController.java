package com.GdtcApi.GdtcApi.Controller;

import com.GdtcApi.GdtcApi.Entities.EmpDepartment;
import com.GdtcApi.GdtcApi.RequestDTO.EmpDepartmentRequest;
import com.GdtcApi.GdtcApi.ResponseDTO.EmpDepartmentResponse;
import com.GdtcApi.GdtcApi.Service.EmpDepartmentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
//@RequestMapping("/departments")
@SecurityRequirement(name = "Bearer Authentication")
public class EmpDepartmentController {

    @Autowired
    private EmpDepartmentService departmentService;

    // Create a new department
    @PostMapping("/departments")
    public CompletableFuture<ResponseEntity<EmpDepartmentResponse>> createDepartment(
            @Valid @RequestBody EmpDepartmentRequest department) {
        return departmentService.upsertDepartmentAsync(department)
                .thenApply(savedDepartment ->
                        new ResponseEntity<>(savedDepartment, HttpStatus.CREATED)
                )
                .exceptionally(ex -> {
                    // Handle exceptions and return an appropriate error response
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    // Get a department by ID
    @GetMapping("/departments/{id}")
    public CompletableFuture<ResponseEntity<EmpDepartmentResponse>> getDepartmentById(@PathVariable int id) {
        return departmentService.getDepartmentByIdAsync(id)
            .thenApply(savedDepartment ->
                    new ResponseEntity<>(savedDepartment, HttpStatus.CREATED)
            )
            .exceptionally(ex -> {
                // Handle exceptions and return an appropriate error response
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            });
    }

    // Get all departments
    @GetMapping("/departments")
    public CompletableFuture<ResponseEntity<List<EmpDepartmentResponse>>> getAllDepartments() {
        return departmentService.getAllDepartmentsAsync()
            .thenApply(savedDepartment ->
                    new ResponseEntity<>(savedDepartment, HttpStatus.CREATED)
            )
            .exceptionally(ex -> {
                // Handle exceptions and return an appropriate error response
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            });
    }

    // Update a department
    @PutMapping("/departments/{id}")
    public ResponseEntity<Integer> updateDepartment(@PathVariable Integer id, @RequestBody EmpDepartment department) {
//        department.setDepartmentId(id);
//        EmpDepartment updatedDepartment = departmentService.updateDepartment(department);
        return ResponseEntity.ok(id);
    }

    // Delete a department
    @DeleteMapping("/departments/{id}")
    public CompletableFuture<ResponseEntity<Boolean>> deleteDepartment(@PathVariable int id) {
        return departmentService.deleteDepartmentAsync(id)
            .thenApply(savedDepartment ->
                    new ResponseEntity<>(savedDepartment, HttpStatus.CREATED)
            )
            .exceptionally(ex -> {
                // Handle exceptions and return an appropriate error response
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            });
    }

    @GetMapping("departments/all")
    public List<EmpDepartmentResponse> getAllSyncDepartments(){
        return departmentService.getAllSyncDepartment();
    }

    @PostMapping("departments/sync")
    public EmpDepartmentResponse addSyncDepartment(@RequestBody EmpDepartmentRequest empDepartmentRequest){
        return departmentService.addSyncDepartment(empDepartmentRequest);
    }
}
