package com.jocata.dao.impl;

import com.jocata.dao.UserDao;
import com.jocata.userservice.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }
    @Override
    public User findById(Integer id) {
        String sql = """
        FROM User u 
        LEFT JOIN FETCH u.roles 
        LEFT JOIN FETCH u.addresses 
        WHERE u.id = :id
    """;

        return entityManager.createQuery(sql, User.class)
                .setParameter("id", id)
                .getSingleResult();
    }
    @Override
    public User findByUsernameAndPassword(String username, String password) {
        String sql = """
            FROM User u 
            LEFT JOIN FETCH u.roles 
            LEFT JOIN FETCH u.addresses 
            WHERE u.username = :username AND u.password = :password
        """;

        List<User> result = entityManager.createQuery(sql, User.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultList();

        return result.isEmpty() ? null : result.get(0);
    }
    @Override
    public User findByUsername(String username) {
        try {
            return entityManager.createQuery(
                            "SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User update(User user) {
        return entityManager.merge(user);
    }
}
