package com.example.sigur.repository;

import com.example.sigur.entity.Department;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class DepartmentRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Department getPDepartmentById(Integer postId) {
        return entityManager.find(Department.class, postId);
    }

    public void addDepartment(Department department) {
        entityManager.persist(department);
    }

    public Long getNumberOfDepartments() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(Department.class)));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    public List<Department> getAllDepartments() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Department> criteriaQuery = criteriaBuilder.createQuery(Department.class);
        Root<Department> postRoot = criteriaQuery.from(Department.class);
        criteriaQuery.select(postRoot);
        TypedQuery<Department> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }
}
