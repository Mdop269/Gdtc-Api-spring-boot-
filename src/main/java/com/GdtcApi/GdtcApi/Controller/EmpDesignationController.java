package com.GdtcApi.GdtcApi.Controller;

import com.GdtcApi.GdtcApi.Entities.EmpDesignation;
import com.GdtcApi.GdtcApi.Interface.IEmpDesignationService;
import com.GdtcApi.GdtcApi.RequestDTO.EmpDepartmentRequest;
import com.GdtcApi.GdtcApi.RequestDTO.EmpDesignationRequest;
import com.GdtcApi.GdtcApi.ResponseDTO.EmpDepartmentResponse;
import com.GdtcApi.GdtcApi.ResponseDTO.EmpDesignationResponse;
import com.GdtcApi.GdtcApi.Service.EmpDesignationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/designations")
@SecurityRequirement(name = "Bearer Authentication")
public class EmpDesignationController {

    @Autowired
    private IEmpDesignationService designationService;

//    // Create a new designation
//    @PostMapping
//    public ResponseEntity<EmpDesignation> createDesignation(@RequestBody EmpDesignation designation) {
//        EmpDesignation savedDesignation = designationService.createDesignation(designation);
//        return new ResponseEntity<>(savedDesignation, HttpStatus.CREATED);
//    }

    // Create a new designation
    @PostMapping
    public CompletableFuture<ResponseEntity<EmpDesignationResponse>> createDesignation(
            @Valid @RequestBody EmpDesignationRequest empDesignationRequest) {
        return designationService.upsertDesignationAsync(empDesignationRequest)
                .thenApply(savedDesignation ->
                        new ResponseEntity<>(savedDesignation, HttpStatus.CREATED)
                )
                .exceptionally(ex -> {
                    // Handle exceptions and return an appropriate error response
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    // Get a designation by ID
    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<EmpDesignationResponse>> getDesignationById(@PathVariable int id) {
        return designationService.getDesignationByIdAsync(id)
                .thenApply(savedDesignation ->
                        new ResponseEntity<>(savedDesignation, HttpStatus.CREATED)
                )
                .exceptionally(ex -> {
                    // Handle exceptions and return an appropriate error response
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    // Get all designations
    @GetMapping
    public CompletableFuture<ResponseEntity<List<EmpDesignationResponse>>> getAllDesignations() {
        return designationService.getAllDesignationsAsync()
                .thenApply(savedDesignation ->
                        new ResponseEntity<>(savedDesignation, HttpStatus.CREATED)
                )
                .exceptionally(ex -> {
                    // Handle exceptions and return an appropriate error response
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    // Update a designation
    @PutMapping("/{id}")
    public ResponseEntity<EmpDesignation> updateDesignation(@PathVariable int id, @RequestBody EmpDesignation designation) {
        designation.setDesignationId(id);
        EmpDesignation updatedDesignation = designationService.updateDesignation(designation);
        return ResponseEntity.ok(updatedDesignation);
    }

    // Delete a designation
    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Boolean>> deleteDesignation(@PathVariable int id) {
        return designationService.deleteDesignationAsync(id)
                .thenApply(savedDesignation ->
                        new ResponseEntity<>(savedDesignation, HttpStatus.CREATED)
                )
                .exceptionally(ex -> {
                    // Handle exceptions and return an appropriate error response
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }
}
