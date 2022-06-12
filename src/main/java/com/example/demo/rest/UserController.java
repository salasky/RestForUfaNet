package com.example.demo.rest;

import com.example.demo.domain.User;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
/**

 *
 * @author salasky
 * https://github.com/salasky/
 */
@RestController
@RequestMapping("/api/v0/pool/client/")
@CrossOrigin("*")
public class UserController {


    private UserService userService;

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

    @PostMapping("/update")
    public ResponseEntity updateUser(@RequestBody User user)
    {
        return userService.updateUser(user);

    }

    @PostMapping("/delete")
    public ResponseEntity deleteUser(@RequestBody User user)
    {
        return userService.deleteUser(user);

    }

}
