package com.GdtcApi.GdtcApi.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "emp_department")
@Data
public class EmpDepartment {

    @Id // it is defining the primamry key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    public Integer departmentId;

    @Size(max = 50)// Bean Validation for application-level checks
    @Column(name = "department_name", nullable = false , length = 50)
    public String departmentName;

    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
    //    the code will go on circular referensing eg EmpDepartment has a @OneToMany relationship with EmpDesignation.
    //EmpDesignation has a @ManyToOne relationship with EmpDepartment.
    //SO thats why we create The Dto

    private List<EmpDesignation> designations;

}
