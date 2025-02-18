package com.GdtcApi.GdtcApi.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "emp_accountDetail")
@Data
public class EmpAccountDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_detail_id")
    public Integer accountDetailId;

    @Size(max = 100)
    @Column(name = "bank_name",nullable = false, length = 100)
    public String bankName;

    @Size(max = 50)
    @Column(name = "ifci_code",nullable = false, length = 50)
    public String ifciCode;

    @Size(max = 50)
    @Column(name = "branch",nullable = false, length = 50)
    public String branch;

    @Size(max = 100)
    @Column(name = "name_on_account",nullable = false, length = 100)
    public String nameOnAccount;

    @Size(max = 50)
    @Column(name = "account_number",nullable = false, length = 50)
    public String accountNumber;

    @ManyToOne
    @JoinColumn(name = "EmployeeId", nullable = false, foreignKey = @ForeignKey(name = "AccountDetail_fk_1"))
    public Employee employee;
}
