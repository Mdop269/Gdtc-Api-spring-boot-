package com.GdtcApi.GdtcApi.Repsoitory;

import com.GdtcApi.GdtcApi.Entities.EmpDesignation;
import com.GdtcApi.GdtcApi.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

    @Async
    @Query(nativeQuery = true, value = "SELECT * FROM emp_employee d  where d.first_name = :first_name And last_name = :last_name")
//    @Query(nativeQuery = true, value = "SELECT * FROM emp_designation d  LEFT JOIN emp_department As de on d.department_id = de.department_id where d.designation_title = :designation_title")
    CompletableFuture<Employee> findByEmployeeName(
            @Param("first_name") String firstName,
            @Param("last_name") String lastName

    );
}
