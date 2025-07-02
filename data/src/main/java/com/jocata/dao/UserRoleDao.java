package com.jocata.dao;

import com.jocata.userservice.entity.UserRole;

public interface UserRoleDao {
    UserRole save(UserRole userRole);
    void deleteByUserId(Integer userId);

}
