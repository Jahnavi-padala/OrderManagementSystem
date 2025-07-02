package com.jocata.dao.impl;

import com.jocata.dao.RoleDao;
import com.jocata.userservice.entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
@Transactional
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role save(Role role) {
        entityManager.persist(role);
        return role;
    }
    @Override
    public Role update(Role role) {
        return entityManager.merge(role);
    }

    @Override
    public Role findById(Integer id) {
        return entityManager.find(Role.class, id);
    }
}
