package com.jocata.dao.impl;

import com.jocata.dao.UserRoleDao;
import com.jocata.userservice.entity.UserRole;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class UserRoleDaoImpl implements UserRoleDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserRole save(UserRole userRole) {
        entityManager.persist(userRole);
        return userRole;
    }
    @Override
    public void deleteByUserId(Integer userId) {
        entityManager.createQuery("DELETE FROM UserRole ur WHERE ur.id.userId = :userId")
                .setParameter("userId", userId)
                .executeUpdate();
    }
}
