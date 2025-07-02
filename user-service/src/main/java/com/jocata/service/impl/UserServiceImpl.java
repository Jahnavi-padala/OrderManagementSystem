package com.jocata.service.impl;

import com.jocata.dao.RoleDao;
import com.jocata.dao.UserAddressDao;
import com.jocata.dao.UserDao;
import com.jocata.dao.UserRoleDao;
import com.jocata.service.UserService;
import com.jocata.userservice.entity.Role;
import com.jocata.userservice.entity.User;
import com.jocata.userservice.entity.UserAddress;
import com.jocata.userservice.entity.UserRole;
import com.jocata.userservice.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;

    @Autowired
    private UserAddressDao userAddressDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserRoleDao userRoleDao;


    @Override
    public UserDataForm createUser(UserDataForm userDataForm) {
        UserForm uf = userDataForm.getUser();


        User existingUser = userDao.findByUsername(uf.getUsername());

        if (existingUser != null) {

            UserDataForm response = new UserDataForm();
            response.setMessage("User already exists with username: " + uf.getUsername());
            return response;
        }

        User user = new User();
        user.setUsername(uf.getUsername());
        user.setPassword(uf.getPassword());
        user.setEmail(uf.getEmail());
        user.setCreatedAt(LocalDateTime.parse(uf.getCreatedAt()));
        user.setUpdatedAt(LocalDateTime.parse(uf.getUpdatedAt()));
        userDao.save(user);

        for (UserAddressForm addressForm : userDataForm.getAddresses()) {
            UserAddress address = new UserAddress();
            address.setAddressLine1(addressForm.getAddressLine1());
            address.setAddressLine2(addressForm.getAddressLine2());
            address.setCity(addressForm.getCity());
            address.setPostalCode(addressForm.getPostalCode());
            address.setCountry(addressForm.getCountry());
            address.setCreatedAt(LocalDateTime.parse(addressForm.getCreatedAt()));
            address.setUpdatedAt(LocalDateTime.parse(addressForm.getUpdatedAt()));
            address.setUser(user);
            userAddressDao.save(address);
        }

        for (RoleForm roleForm : userDataForm.getRoles()) {
            Role role = new Role();
            role.setRoleName(roleForm.getRoleName());
            role.setCreatedAt(LocalDateTime.parse(roleForm.getCreatedAt()));
            role.setUpdatedAt(LocalDateTime.parse(roleForm.getUpdatedAt()));
            roleDao.save(role);

            UserRole userRole = new UserRole(user, role);
            userRoleDao.save(userRole);
        }

        UserDataForm response = mapUserToUserDataForm(user);
        response.setMessage("User created successfully");
        return response;
    }


    @Override
    public UserDataForm getUserById(String id) {
        Integer intId = Integer.valueOf(id);
        User user = userDao.findById(intId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return mapUserToUserDataForm(user);
    }


        private UserDataForm mapUserToUserDataForm(User user) {

            UserForm userForm = new UserForm();
            userForm.setId(String.valueOf(user.getId()));
            userForm.setUsername(user.getUsername());
            userForm.setEmail(user.getEmail());
            userForm.setPassword(user.getPassword());
            userForm.setCreatedAt(user.getCreatedAt() != null ? user.getCreatedAt().toString() : null);
            userForm.setUpdatedAt(user.getUpdatedAt() != null ? user.getUpdatedAt().toString() : null);

            List<UserAddressForm> addressForms = new ArrayList<>();
            if (user.getAddresses() != null) {
                for (UserAddress address : user.getAddresses()) {
                    UserAddressForm addressForm = new UserAddressForm();
                    addressForm.setId(String.valueOf(address.getId()));
                    addressForm.setAddressLine1(address.getAddressLine1());
                    addressForm.setAddressLine2(address.getAddressLine2());
                    addressForm.setCity(address.getCity());
                    addressForm.setPostalCode(address.getPostalCode());
                    addressForm.setCountry(address.getCountry());
                    addressForm.setCreatedAt(address.getCreatedAt() != null ? address.getCreatedAt().toString() : null);
                    addressForm.setUpdatedAt(address.getUpdatedAt() != null ? address.getUpdatedAt().toString() : null);
                    addressForm.setUserId(String.valueOf(user.getId()));
                    addressForms.add(addressForm);
                }
            }


            List<RoleForm> roleForms = new ArrayList<>();
            if (user.getRoles() != null) {
                for (Role role : user.getRoles()) {
                    RoleForm roleForm = new RoleForm();
                    roleForm.setId(String.valueOf(role.getId()));
                    roleForm.setRoleName(role.getRoleName());
                    roleForm.setCreatedAt(role.getCreatedAt() != null ? role.getCreatedAt().toString() : null);
                    roleForm.setUpdatedAt(role.getUpdatedAt() != null ? role.getUpdatedAt().toString() : null);
                    roleForms.add(roleForm);
                }
            }


            UserDataForm userDataForm = new UserDataForm();
            userDataForm.setUser(userForm);
            userDataForm.setAddresses(addressForms);
            userDataForm.setRoles(roleForms);

            return userDataForm;
        }
    @Override
    public UserDataForm login(UserDataForm userDataForm) {
        String username = userDataForm.getUser().getUsername();
        String password = userDataForm.getUser().getPassword();

        User user = userDao.findByUsernameAndPassword(username, password);
        if (user == null) {
            UserDataForm response = new UserDataForm();
            response.setMessage("Invalid username or password");
            return response;
        }
        UserDataForm response = mapUserToUserDataForm(user);
        response.setMessage("Login successful");
        return response;
    }
    @Override
    public UserDataForm updateUser(String id, UserDataForm userDataForm) {
        Integer intId = Integer.parseInt(id);
        User user = userDao.findById(intId);
        if (user == null) {
            UserDataForm response = new UserDataForm();
            response.setMessage("User not found");
            return response;
        }

        UserForm uf = userDataForm.getUser();
        user.setUsername(uf.getUsername());
        user.setPassword(uf.getPassword());
        user.setEmail(uf.getEmail());
        user.setUpdatedAt(LocalDateTime.now());
        userDao.update(user);


        for (UserAddressForm af : userDataForm.getAddresses()) {
            UserAddress address = userAddressDao.findById(Integer.parseInt(af.getId()));
            if (address != null) {
                address.setAddressLine1(af.getAddressLine1());
                address.setAddressLine2(af.getAddressLine2());
                address.setCity(af.getCity());
                address.setPostalCode(af.getPostalCode());
                address.setCountry(af.getCountry());
                address.setUpdatedAt(LocalDateTime.now());
                address.setUser(user);
                userAddressDao.update(address);
            }
        }

        userRoleDao.deleteByUserId(user.getId());
        for (RoleForm rf : userDataForm.getRoles()) {
            Role role = roleDao.findById(Integer.parseInt(rf.getId()));
            if (role != null) {
                role.setRoleName(rf.getRoleName());
                role.setUpdatedAt(LocalDateTime.now());
                roleDao.update(role);

                UserRole userRole = new UserRole(user, role);
                userRoleDao.save(userRole);
            }
        }

        UserDataForm response = mapUserToUserDataForm(user);
        response.setMessage("User updated successfully");
        return response;
    }






}
