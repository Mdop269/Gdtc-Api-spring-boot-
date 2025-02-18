package com.GdtcApi.GdtcApi.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "emp_expenseType")
@Data
public class EmpExpenseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expensetype_id")
    public Integer expenseTypeId;

    @Size(max = 50)
    @Column(name = "expensetype_name", nullable = false, length = 50)
    public String expenseTypeName;

    @OneToMany(mappedBy = "expenseType")
    public List<EmpExpense> expenses;

}
