package com.jocata.dao;

import com.jocata.userservice.entity.User;

public interface UserDao {
    User save(User user);
    User findById(Integer id);

    User findByUsernameAndPassword(String username, String password);

    User update(User user);
    User findByUsername(String username);
}
