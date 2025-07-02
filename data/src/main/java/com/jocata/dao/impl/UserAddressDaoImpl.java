package com.jocata.dao.impl;

import com.jocata.dao.UserAddressDao;
import com.jocata.userservice.entity.UserAddress;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class UserAddressDaoImpl implements UserAddressDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserAddress save(UserAddress address) {
        entityManager.persist(address);
        return address;
    }
    @Override
    public UserAddress update(UserAddress address) {
        return entityManager.merge(address);
    }

    @Override
    public UserAddress findById(Integer id) {
        return entityManager.find(UserAddress.class, id);
    }
    @Override
    public List<UserAddress> findByUserId(Integer userId) {
        String jpql = "SELECT ua FROM UserAddress ua WHERE ua.user.id = :userId";
        return entityManager.createQuery(jpql, UserAddress.class)
                .setParameter("userId", userId)
                .getResultList();
    }

}
