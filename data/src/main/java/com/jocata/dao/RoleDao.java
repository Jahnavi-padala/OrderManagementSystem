package com.jocata.dao;

import com.jocata.userservice.entity.Role;

public interface RoleDao {
    Role save(Role role);
    Role update(Role role);
    Role findById(Integer id);
}
