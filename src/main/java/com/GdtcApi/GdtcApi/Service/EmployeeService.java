package com.GdtcApi.GdtcApi.Service;

import com.GdtcApi.GdtcApi.Entities.EmpDepartment;
import com.GdtcApi.GdtcApi.Entities.EmpDesignation;
import com.GdtcApi.GdtcApi.Entities.Employee;
import com.GdtcApi.GdtcApi.Repsoitory.EmpDepartmentRepo;
import com.GdtcApi.GdtcApi.Repsoitory.EmpDesignationRepo;
import com.GdtcApi.GdtcApi.Repsoitory.EmployeeRepo;
import com.GdtcApi.GdtcApi.RequestDTO.EmpDesignationRequest;
import com.GdtcApi.GdtcApi.RequestDTO.EmployeeRequest;
import com.GdtcApi.GdtcApi.ResponseDTO.EmpDesignationResponse;
import com.GdtcApi.GdtcApi.ResponseDTO.EmployeeResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepo _employeeRepo;

    @Autowired
    EmpDesignationRepo _employeeDesignationRepo;


    @Async
    @Transactional // this is used for rollback means if we got error and before it changed something it will roll back
    public CompletableFuture<EmployeeResponse> upsertEmployeeAsync(EmployeeRequest dto) {

        //challenge if anyone can make this async without changing the return type
        Employee manager = _employeeRepo.findById(dto.getManagerId()).orElse(new Employee());

        EmpDesignation empDesignation = _employeeDesignationRepo.findById(dto.getDesignationId()).orElse(new EmpDesignation());


        // Convert DTO to entity
        Employee employeeEntity = EmployeeRequest.MapToEntity(dto, manager, empDesignation);

        // Start async processing chain
        return _employeeRepo.findByEmployeeName(employeeEntity.getFirstName(), employeeEntity.getLastName())
                .thenComposeAsync(existingEmployee -> {
                    if (existingEmployee != null) {
                        // Update existing entity
                        existingEmployee.setBirthDate(employeeEntity.getBirthDate());
                        existingEmployee.setProbation(employeeEntity.getProbation());
                        existingEmployee.setSalary(employeeEntity.getSalary());
                        existingEmployee.setHireDate(employeeEntity.getHireDate());
                        existingEmployee.setManager(employeeEntity.getManager());
                        existingEmployee.setDesignation(employeeEntity.getDesignation());


                        // Save the entity and map to DTO
                        return CompletableFuture.supplyAsync(() -> {
                            Employee updated = _employeeRepo.save(existingEmployee);
                            return EmployeeResponse.MapToDto(updated);
                        });
                    } else {
                        // Create new entity
                        return CompletableFuture.supplyAsync(() -> {
                            Employee saved = _employeeRepo.save(employeeEntity);
                            return EmployeeResponse.MapToDto(saved);
                        });
                    }
                })
                .exceptionally(ex -> {
                    throw new CompletionException("Failed to upsert designation", ex.getCause());
                });
    }

//
//    @Async
//    public CompletableFuture<EmpDesignationResponse> getDesignationByIdAsync(int id) {
//        return CompletableFuture.supplyAsync(() -> {
//            EmpDesignation designation = _empDesignationRepo.findById(id)
//                    .orElseThrow(() -> new RuntimeException("Designation not found"));
//            return EmpDesignationResponse.MapToDto(designation);
//        });
//    }
//
//    @Async // Use Spring-managed thread pool
//    public CompletableFuture<List<EmpDesignationResponse>> getAllDesignationsAsync() {
//        return CompletableFuture.supplyAsync(() -> {
//            // Blocking JPA call wrapped in async
//            List<EmpDesignation> designations = _empDesignationRepo.findAll();
//
//            // Map entities to DTOs
//            return designations.stream()
//                    .map(EmpDesignationResponse::MapToDto)
//                    .collect(Collectors.toList());
//        });
//    }
//
//    @Override
//    public EmpDesignation updateDesignation(EmpDesignation designation) {
//        return _empDesignationRepo.save(designation);
//    }
//
//    @Async
//    public CompletableFuture<Boolean> deleteDesignationAsync(int id) {
//        return CompletableFuture.supplyAsync(() -> {
//            _empDesignationRepo.deleteById(id);
//            return true;
//        });
//    }
}
