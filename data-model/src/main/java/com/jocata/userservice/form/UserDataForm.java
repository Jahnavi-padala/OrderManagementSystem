package com.jocata.userservice.form;

import java.util.List;

public class UserDataForm {
    private UserForm user;
    private List<UserAddressForm> addresses;
    private List<RoleForm> roles;
    private String message;
    public UserForm getUser() {
        return user;
    }

    public void setUser(UserForm user) {
        this.user = user;
    }

    public List<UserAddressForm> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<UserAddressForm> addresses) {
        this.addresses = addresses;
    }

    public List<RoleForm> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleForm> roles) {
        this.roles = roles;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
