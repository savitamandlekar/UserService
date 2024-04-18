package com.in.user.controllers;

import com.in.user.entities.User;
import com.in.user.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
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
    int retryCount=1;
    @GetMapping("/{userId}")
    @CircuitBreaker(name="ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
    // @Retry(name="ratingHotelService",fallbackMethod = "ratingHotelFallback")
    //@RateLimiter(name="userRatelimiter",fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
        System.out.println("retryCount:"+retryCount);
        retryCount++;
        User user=service.getUser(userId);
        return  ResponseEntity.ok(user);
    }


    public ResponseEntity<User> ratingHotelFallback(String userId,Exception ex){
 //System.out.println("Fallback is executed because service is down : "+ex.getMessage());

        User user= User.builder().
         email("dummy@gmail.com").
         name("Dummy").
         about("This is user created dummy some services is down").
         userId("12345").
         build();
 return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping
    public  ResponseEntity<List<User>> getAllUser(){
        List<User> allUser = service.getAllUser();
        return ResponseEntity.ok(allUser);
    }


}
