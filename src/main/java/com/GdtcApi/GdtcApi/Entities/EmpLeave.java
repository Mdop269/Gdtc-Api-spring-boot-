package com.GdtcApi.GdtcApi.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "emp_leave")
@Data
public class EmpLeave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leave_id")
    public Integer leaveId;

    @Column(name = "from_date",nullable = false)
    public LocalDateTime fromDate;

    @Column(name = "to_date",nullable = false)
    public LocalDateTime toDate;

    @Size(max = 100)
    @Column(name = "note",length = 100)
    public String note;

    @Column(name = "approved",columnDefinition = "BOOLEAN default false")
    public Boolean approved;

    @Size(max = 50)
    @Column(name = "status",length = 50)
    public String status;

    @ManyToOne
    @JoinColumn(name = "ApproverId", nullable = false, foreignKey = @ForeignKey(name = "Leaves_fk_2"))
    public Employee approver;

    @ManyToOne
    @JoinColumn(name = "LeaveTypeId", nullable = false, foreignKey = @ForeignKey(name = "Leaves_fk_3"))
    public EmpLeaveType leaveType;

    @ManyToOne
    @JoinColumn(name = "EmployeeId", nullable = false, foreignKey = @ForeignKey(name = "Leaves_fk_1"))
    public Employee employee;

}
