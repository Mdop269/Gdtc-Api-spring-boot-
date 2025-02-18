package com.GdtcApi.GdtcApi.Service;

import com.GdtcApi.GdtcApi.Entities.EmpDepartment;
import com.GdtcApi.GdtcApi.Entities.EmpDesignation;
import com.GdtcApi.GdtcApi.Interface.IEmpDepartmentService;
import com.GdtcApi.GdtcApi.Interface.IEmpDesignationService;
import com.GdtcApi.GdtcApi.Repsoitory.EmpDepartmentRepo;
import com.GdtcApi.GdtcApi.Repsoitory.EmpDesignationRepo;
import com.GdtcApi.GdtcApi.RequestDTO.EmpDepartmentRequest;
import com.GdtcApi.GdtcApi.RequestDTO.EmpDesignationRequest;
import com.GdtcApi.GdtcApi.ResponseDTO.EmpDepartmentResponse;
import com.GdtcApi.GdtcApi.ResponseDTO.EmpDesignationResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

@Service
public class EmpDesignationService implements IEmpDesignationService {

    @Autowired
    EmpDesignationRepo _empDesignationRepo;

    @Autowired
    EmpDepartmentRepo _empDepartmentRepo;


    @Async
    @Transactional // this is used for rollback means if we got error and before it changed something it will roll back
    public CompletableFuture<EmpDesignationResponse> upsertDesignationAsync(EmpDesignationRequest dto) {

        //challenge if anyone can make this async without changing the return type
        EmpDepartment empDepartment = _empDepartmentRepo.findById(dto.getDepartmentId()).orElse(new EmpDepartment());


        // Convert DTO to entity
        EmpDesignation designationEntity = EmpDesignationRequest.MapToEntity(dto, empDepartment);

        // Start async processing chain
        return _empDesignationRepo.findByDesignation(designationEntity.getDesignationTitle())
                .thenComposeAsync(existingDesignation -> {
                    if (existingDesignation != null) {
                        // Update existing entity
                        existingDesignation.setDepartment(designationEntity.getDepartment());

                        // Save the entity and map to DTO
                        return CompletableFuture.supplyAsync(() -> {
                            EmpDesignation updated = _empDesignationRepo.save(existingDesignation);
                            return EmpDesignationResponse.MapToDto(updated);
                        });
                    } else {
                        // Create new entity
                        return CompletableFuture.supplyAsync(() -> {
                            EmpDesignation saved = _empDesignationRepo.save(designationEntity);
                            return EmpDesignationResponse.MapToDto(saved);
                        });
                    }
                })
                .exceptionally(ex -> {
                    throw new CompletionException("Failed to upsert designation", ex.getCause());
                });
    }


    @Async
    public CompletableFuture<EmpDesignationResponse> getDesignationByIdAsync(int id) {
        return CompletableFuture.supplyAsync(() -> {
            EmpDesignation designation = _empDesignationRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Designation not found"));
            return EmpDesignationResponse.MapToDto(designation);
        });
    }

    @Async // Use Spring-managed thread pool
    public CompletableFuture<List<EmpDesignationResponse>> getAllDesignationsAsync() {
        return CompletableFuture.supplyAsync(() -> {
            // Blocking JPA call wrapped in async
            List<EmpDesignation> designations = _empDesignationRepo.findAll();

            // Map entities to DTOs
            return designations.stream()
                    .map(EmpDesignationResponse::MapToDto)
                    .collect(Collectors.toList());
        });
    }

    @Override
    public EmpDesignation updateDesignation(EmpDesignation designation) {
        return _empDesignationRepo.save(designation);
    }

    @Async
    public CompletableFuture<Boolean> deleteDesignationAsync(int id) {
        return CompletableFuture.supplyAsync(() -> {
            _empDesignationRepo.deleteById(id);
            return true;
        });
    }
}
