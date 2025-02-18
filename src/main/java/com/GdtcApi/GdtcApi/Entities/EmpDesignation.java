package com.GdtcApi.GdtcApi.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "emp_designation")
@Data
public class EmpDesignation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "designation_id")
    public Integer designationId;

    @Size(max = 50)// Bean Validation for application-level checks
    @Column(name = "designation_title", nullable = false , length = 50, unique = true)
    public String designationTitle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false, foreignKey = @ForeignKey(name = "Designation_fk_1"))
    //    the code will go on circular referensing eg EmpDepartment has a @OneToMany relationship with EmpDesignation.
//EmpDesignation has a @ManyToOne relationship with EmpDepartment.
//    SO thats why we create The Dto
    public EmpDepartment department;

    @OneToMany(mappedBy = "designation")
    public List<Employee> employees;

}
