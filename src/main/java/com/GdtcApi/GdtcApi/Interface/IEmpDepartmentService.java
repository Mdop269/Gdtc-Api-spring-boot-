package com.GdtcApi.GdtcApi.Interface;

import com.GdtcApi.GdtcApi.Entities.EmpDepartment;
import com.GdtcApi.GdtcApi.RequestDTO.EmpDepartmentRequest;
import com.GdtcApi.GdtcApi.ResponseDTO.EmpDepartmentResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IEmpDepartmentService {
    CompletableFuture<EmpDepartmentResponse> upsertDepartmentAsync(EmpDepartmentRequest department);
    CompletableFuture<EmpDepartmentResponse> getDepartmentByIdAsync(int id);
    CompletableFuture<List<EmpDepartmentResponse>> getAllDepartmentsAsync();
    EmpDepartment updateDepartment(EmpDepartment department);
    CompletableFuture<Boolean> deleteDepartmentAsync(int id);
}
