package com.GdtcApi.GdtcApi.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "emp_expense")
@Data
public class EmpExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    public Integer expenseId;

    @Column(name = "local_datetime",nullable = false)
    public LocalDateTime date;

    @Column(name = "cost",nullable = false, scale = 2)
    public BigDecimal cost;

    @Size(max = 45)
    @Column(name = "status",length = 45)
    public String status;

    @ManyToOne
    @JoinColumn(name = "EmployeeId", nullable = false, foreignKey = @ForeignKey(name = "Expense_fk_1"))
    public Employee employee;

    @ManyToOne
    @JoinColumn(name = "ApproverId", nullable = false, foreignKey = @ForeignKey(name = "Expense_fk_2"))
    public Employee approver;

    @ManyToOne
    @JoinColumn(name = "ExpenseTypeId", nullable = false, foreignKey = @ForeignKey(name = "Expense_fk_3"))
    public EmpExpenseType expenseType;

    @Size(max = 100)
    @Column(length = 100)
    public String comment;
}
