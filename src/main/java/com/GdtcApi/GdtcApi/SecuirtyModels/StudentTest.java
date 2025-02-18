package com.GdtcApi.GdtcApi.SecuirtyModels;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class StudentTest {

    @Id
    public Integer studentId;

    public String firstName;

    public String lastName;

    public StudentTest() {
    }

    public StudentTest(Integer studentId, String firstName, String lastName) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
