package com.GdtcApi.GdtcApi.Repsoitory;

import com.GdtcApi.GdtcApi.Entities.EmpDepartment;
import com.GdtcApi.GdtcApi.Entities.EmpDesignation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Repository
public interface EmpDesignationRepo extends JpaRepository<EmpDesignation, Integer> {

    @Async
    @Query(nativeQuery = true, value = "SELECT * FROM emp_designation d  where d.designation_title = :designation_title")
//    @Query(nativeQuery = true, value = "SELECT * FROM emp_designation d  LEFT JOIN emp_department As de on d.department_id = de.department_id where d.designation_title = :designation_title")
    CompletableFuture<EmpDesignation> findByDesignation(
            @Param("designation_title") String designationTitle
    );
}
