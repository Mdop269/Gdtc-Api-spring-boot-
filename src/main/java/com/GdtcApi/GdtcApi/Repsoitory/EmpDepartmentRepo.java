package com.GdtcApi.GdtcApi.Repsoitory;

import com.GdtcApi.GdtcApi.Entities.EmpDepartment;
import com.GdtcApi.GdtcApi.ResponseDTO.EmpDepartmentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public interface EmpDepartmentRepo extends JpaRepository<EmpDepartment, Integer> {

    @Async
    @Query(nativeQuery = true , value = "SELECT * FROM emp_department b WHERE b.department_name  = :department_name")
    CompletableFuture<EmpDepartment> findByDepartment(
            @Param("department_name") String departmentName
    );



}
