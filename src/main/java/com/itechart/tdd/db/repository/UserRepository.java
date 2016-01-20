package com.itechart.tdd.db.repository;

import com.itechart.tdd.db.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class UserRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    public List<User> findAll() {
        return findAll(0, Integer.MAX_VALUE);
    }

    public List<User> findAll(int page, int size) {
        String q = "select e from " + User.class.getSimpleName() + " e";
        Query query = entityManager.createQuery(q);

        if (size != 0) {
            query.setFirstResult(page * size);
            query.setMaxResults(size);
        }

        return query.getResultList();
    }

    public List<User> findByName(String name) {
        String q = "select e from " + User.class.getSimpleName() + " e where e.name like '%" + name + "%'";
        Query query = entityManager.createQuery(q);

        return query.getResultList();
    }

    public User save(User entity) {
        entityManager.persist(entity);
        return entity;
    }

    public void delete(long id) {
        entityManager.remove(entityManager.getReference(User.class, id));
    }

}
