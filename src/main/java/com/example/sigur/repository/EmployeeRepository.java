package com.example.sigur.repository;

import com.example.sigur.entity.Employee;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;


@Repository
@Transactional
public class EmployeeRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean isCardCodeExists(byte[] cardCode) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        Predicate employeePredicate = criteriaBuilder.equal(employeeRoot.get("card"), cardCode);
        criteriaQuery.select(employeeRoot).where(employeePredicate);
        TypedQuery<Employee> typedQuery = entityManager.createQuery(criteriaQuery);
        try {
            typedQuery.getSingleResult();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Employee getEmployeeByCard(byte[] cardCode) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        Predicate employeePredicate = criteriaBuilder.equal(employeeRoot.get("card"), cardCode);
        criteriaQuery.select(employeeRoot).where(employeePredicate);
        TypedQuery<Employee> typedQuery = entityManager.createQuery(criteriaQuery);
        try {
            return typedQuery.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public void addPerson(Employee person) {
        entityManager.persist(person);
    }

    public void editEmployee(Date firedTime, Integer id) {
        Employee employee = entityManager.find(Employee.class, id);
        employee.setFiredTime(firedTime);
        entityManager.persist(employee);
    }

    public List<Employee> getAllEmployeesWithNoFiredTime() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> postRoot = criteriaQuery.from(Employee.class);
        Predicate employeePredicate = criteriaBuilder.isNull(postRoot.get("firedTime"));
        criteriaQuery.select(postRoot).where(employeePredicate);
        TypedQuery<Employee> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    public List<Employee> getAllEmployeesWithFiredTime() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> postRoot = criteriaQuery.from(Employee.class);
        Predicate employeePredicate = criteriaBuilder.isNotNull(postRoot.get("firedTime"));
        criteriaQuery.select(postRoot).where(employeePredicate);
        TypedQuery<Employee> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    public List<Employee> getAllEmployees() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> postRoot = criteriaQuery.from(Employee.class);
        criteriaQuery.select(postRoot);
        TypedQuery<Employee> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }
}
