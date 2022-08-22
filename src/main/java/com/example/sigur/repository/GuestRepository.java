package com.example.sigur.repository;

import com.example.sigur.entity.Employee;
import com.example.sigur.entity.Guest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class GuestRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Guest getGuestByEmployee(Employee employee) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Guest> criteriaQuery = criteriaBuilder.createQuery(Guest.class);
        Root<Guest> guestRoot = criteriaQuery.from(Guest.class);
        Predicate guestPredicate = criteriaBuilder.equal(guestRoot.get("employee"), employee);
        criteriaQuery.select(guestRoot).where(guestPredicate);
        TypedQuery<Guest> typedQuery = entityManager.createQuery(criteriaQuery);
        try {
            return typedQuery.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public void removeEmployeesVisitDate(Integer id) {
        Guest guest = entityManager.find(Guest.class, id);
        guest.setVisitDate(null);
        entityManager.persist(guest);
    }

    public void addGuest(Guest guest) {
        entityManager.persist(guest);
    }

    public boolean isCardCodeExists(byte[] cardCode) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Guest> criteriaQuery = criteriaBuilder.createQuery(Guest.class);
        Root<Guest> guestRoot = criteriaQuery.from(Guest.class);
        Predicate guestPredicate = criteriaBuilder.equal(guestRoot.get("card"), cardCode);
        criteriaQuery.select(guestRoot).where(guestPredicate);
        TypedQuery<Guest> typedQuery = entityManager.createQuery(criteriaQuery);
        try {
            typedQuery.getSingleResult();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Guest getGuestByCard(byte[] cardCode) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Guest> criteriaQuery = criteriaBuilder.createQuery(Guest.class);
        Root<Guest> guestRoot = criteriaQuery.from(Guest.class);
        Predicate guestPredicate = criteriaBuilder.equal(guestRoot.get("card"), cardCode);
        criteriaQuery.select(guestRoot).where(guestPredicate);
        TypedQuery<Guest> typedQuery = entityManager.createQuery(criteriaQuery);
        try {
            return typedQuery.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Guest> getAllGuests() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Guest> criteriaQuery = criteriaBuilder.createQuery(Guest.class);
        Root<Guest> postRoot = criteriaQuery.from(Guest.class);
        criteriaQuery.select(postRoot);
        TypedQuery<Guest> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }
}
