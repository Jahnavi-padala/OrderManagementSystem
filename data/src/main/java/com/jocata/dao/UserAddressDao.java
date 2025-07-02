package com.jocata.dao;

import com.jocata.userservice.entity.UserAddress;

import java.util.List;

public interface UserAddressDao {
    UserAddress save(UserAddress address);
    UserAddress update(UserAddress address);
    UserAddress findById(Integer id);
    List<UserAddress> findByUserId(Integer userId);

}
