package com.in.user.controllers;

import com.in.user.entities.User;
import com.in.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User user1=service.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
        User user=service.getUser(userId);
        return  ResponseEntity.ok(user);
    }

    @GetMapping
    public  ResponseEntity<List<User>> getAllUser(){
        List<User> allUser = service.getAllUser();
        return ResponseEntity.ok(allUser);
    }

}
