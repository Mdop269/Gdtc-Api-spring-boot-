package com.GdtcApi.GdtcApi.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "emp_leavetype")
@Data
public class EmpLeaveType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leavetype_id")
    public Integer leaveTypeId;

    @Size(max = 50)
    @Column(name = "leavetype_name", nullable = false, length = 50)
    public String leaveTypeName;

    @OneToMany(mappedBy = "leaveType")
    public List<EmpLeave> leaves;
}
