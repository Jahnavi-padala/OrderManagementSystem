package com.jocata.service;

import com.jocata.userservice.form.UserAddressForm;
import com.jocata.userservice.form.UserDataForm;
import com.jocata.userservice.form.UserForm;

import java.util.List;

public interface UserService {
    UserDataForm createUser(UserDataForm userDataForm);
    UserDataForm getUserById(String id);
    UserDataForm login(UserDataForm userDataForm);
    UserDataForm updateUser(String id, UserDataForm userDataForm);


}
