package com.example.demo.rest;

import com.example.demo.domain.User;
import com.example.demo.domain.UserDTO;
import com.example.demo.service.UserService;
import com.example.demo.userValidation.UserValidate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v0/pool/client/")
@CrossOrigin("*")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;
    private UserValidate userValidate;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<UserDTO> getAllUser()
    {
        return userService.getAllUser();
    }

    @PostMapping("/add")
    public ResponseEntity saveUser(@RequestBody User user)
    {
       return userService.createUser(user);

    }

    @GetMapping("/get/{id}")
    public Optional<User> getUser(@PathVariable long id) {
        Optional<User> user=userService.getUserById(id);
        return user;
    }

}