package com.example.sigur.entity;

import com.example.sigur.model.PersonType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "guest", schema = "sigur")
public class Guest extends Person {
    {
        this.setType(PersonType.GUEST);
    }

    @Column(name = "VISIT_DATE")
    private Date visitDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMP_ID", nullable = false)
    private Employee employee;

    public Guest(byte[] card, Date visitDate, Employee employee) {
        super(card, PersonType.GUEST);
        this.visitDate = visitDate;
        this.employee = employee;
    }

    public Guest() {
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
