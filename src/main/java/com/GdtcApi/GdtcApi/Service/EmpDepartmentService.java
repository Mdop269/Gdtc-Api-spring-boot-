package com.GdtcApi.GdtcApi.Service;

import com.GdtcApi.GdtcApi.Entities.EmpDepartment;
import com.GdtcApi.GdtcApi.Interface.IEmpDepartmentService;
import com.GdtcApi.GdtcApi.Repsoitory.EmpDepartmentRepo;
import com.GdtcApi.GdtcApi.RequestDTO.EmpDepartmentRequest;
import com.GdtcApi.GdtcApi.ResponseDTO.EmpDepartmentResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

@Service
public class EmpDepartmentService implements IEmpDepartmentService {

    @Autowired
    EmpDepartmentRepo _empDepartmentRepo;


    @Async("tenantAwareExecutor")
    @Transactional // this is used for rollback means if we got error and before it changed something it will roll back
    public CompletableFuture<EmpDepartmentResponse> upsertDepartmentAsync(EmpDepartmentRequest dto) {
        // Convert DTO to entity
        EmpDepartment departmentEntity = EmpDepartmentRequest.MapToEntity(dto);

        // Start async processing chain
        return _empDepartmentRepo.findByDepartment(departmentEntity.getDepartmentName())
                .thenComposeAsync(existingDepartment -> {
                    if (existingDepartment != null) {
                        // Update existing entity
                        existingDepartment.setDepartmentName(departmentEntity.getDepartmentName());

                        // Save the entity and map to DTO
                        return CompletableFuture.supplyAsync(() -> {
                            EmpDepartment updated = _empDepartmentRepo.save(existingDepartment);
                            return EmpDepartmentResponse.MapToDto(updated);
                        });
                    } else {
                        // Create new entity
                        return CompletableFuture.supplyAsync(() -> {
                            EmpDepartment saved = _empDepartmentRepo.save(departmentEntity);
                            return EmpDepartmentResponse.MapToDto(saved);
                        });
                    }
                })
                .exceptionally(ex -> {
                    throw new CompletionException("Failed to upsert department", ex.getCause());
                });
    }

    @Async("tenantAwareExecutor")
    public CompletableFuture<EmpDepartmentResponse> getDepartmentByIdAsync(int id) {
        return CompletableFuture.supplyAsync(() -> {
            EmpDepartment department = _empDepartmentRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            return EmpDepartmentResponse.MapToDto(department);
        });
    }

    @Async("tenantAwareExecutor") // Use Spring-managed thread pool
    public CompletableFuture<List<EmpDepartmentResponse>> getAllDepartmentsAsync() {
        return CompletableFuture.supplyAsync(() -> {
            // Blocking JPA call wrapped in async
            List<EmpDepartment> departments = _empDepartmentRepo.findAll();

            // Map entities to DTOs
            return departments.stream()
                    .map(EmpDepartmentResponse::MapToDto)
                    .collect(Collectors.toList());
        });
    }

    @Override
    public EmpDepartment updateDepartment(EmpDepartment department) {
        return _empDepartmentRepo.save(department);
    }

    @Async("tenantAwareExecutor")
    public CompletableFuture<Boolean> deleteDepartmentAsync(int id) {
        return CompletableFuture.supplyAsync(() -> {
            _empDepartmentRepo.deleteById(id);
            return true;
        });
    }

    public List<EmpDepartmentResponse> getAllSyncDepartment(){
        List<EmpDepartment> departments = _empDepartmentRepo.findAll();

        // Map entities to DTOs
        return departments.stream()
                .map(EmpDepartmentResponse::MapToDto)
                .collect(Collectors.toList());
    }

    public EmpDepartmentResponse addSyncDepartment(EmpDepartmentRequest empDepartmentRequest) {
        EmpDepartment departmentEntity = EmpDepartmentRequest.MapToEntity(empDepartmentRequest);
        EmpDepartment saved =  _empDepartmentRepo.save(departmentEntity);
        return EmpDepartmentResponse.MapToDto(saved);
    }
}
