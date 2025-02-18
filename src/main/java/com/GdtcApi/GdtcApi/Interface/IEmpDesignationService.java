package com.GdtcApi.GdtcApi.Interface;

import com.GdtcApi.GdtcApi.Entities.EmpDesignation;
import com.GdtcApi.GdtcApi.RequestDTO.EmpDesignationRequest;
import com.GdtcApi.GdtcApi.ResponseDTO.EmpDesignationResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IEmpDesignationService {

    CompletableFuture<EmpDesignationResponse> getDesignationByIdAsync(int id);
    CompletableFuture<List<EmpDesignationResponse>> getAllDesignationsAsync();
    EmpDesignation updateDesignation(EmpDesignation designation);
    CompletableFuture<Boolean> deleteDesignationAsync(int id);
    CompletableFuture<EmpDesignationResponse> upsertDesignationAsync(EmpDesignationRequest dto);
}
