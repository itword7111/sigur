package com.example.sigur.entity;

import com.example.sigur.model.PersonType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employee", schema = "sigur")
public class Employee extends Person {
    {
        this.setType(PersonType.EMPLOYEE);
    }

    @Column(name = "HIRE_TIME")
    private Date hireTime;
    @Column(name = "FIRED_TIME")
    private Date firedTime;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DEPARTMENT_ID", nullable = false)
    private Department department;

    public Employee() {
    }

    public Employee(byte[] card, Date hireTime) {
        super(card, PersonType.EMPLOYEE);
        this.hireTime = hireTime;
    }

    public Date getHireTime() {
        return hireTime;
    }

    public void setHireTime(Date hireTime) {
        this.hireTime = hireTime;
    }

    public Date getFiredTime() {
        return firedTime;
    }

    public void setFiredTime(Date firedTime) {
        this.firedTime = firedTime;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
