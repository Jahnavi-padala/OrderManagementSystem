package com.jocata.controller;

import com.jocata.service.UserService;
import com.jocata.userservice.form.UserAddressForm;
import com.jocata.userservice.form.UserDataForm;
import com.jocata.userservice.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/createUser")
    public ResponseEntity<UserDataForm> createUser(@RequestBody UserDataForm userDataForm) {
        UserDataForm createdUser = userService.createUser(userDataForm);
        return ResponseEntity.ok(createdUser);
    }
    @PostMapping("/login")
    public ResponseEntity<UserDataForm> login(@RequestBody UserDataForm userDataForm) {
        UserDataForm loggedInUser = userService.login(userDataForm);
        return ResponseEntity.ok(loggedInUser);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UserDataForm> updateUser(@PathVariable String id, @RequestBody UserDataForm userDataForm) {
        UserDataForm updatedUser = userService.updateUser(id, userDataForm);
        return ResponseEntity.ok(updatedUser);
    }
    @GetMapping("/getUserById/{id}")
    public ResponseEntity<UserDataForm> getUserById(@PathVariable String id) {
        UserDataForm userData = userService.getUserById(id);
        return ResponseEntity.ok(userData);
    }



}
