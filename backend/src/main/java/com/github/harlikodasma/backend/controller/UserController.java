package com.github.harlikodasma.backend.controller;

import com.github.harlikodasma.backend.bean.UserQueryReturn;
import com.github.harlikodasma.backend.model.User;
import com.github.harlikodasma.backend.service.UserService;
import io.swagger.annotations.ApiOperation;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation("Register new user")
    @PostMapping("register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @ApiOperation("Login user")
    @PostMapping("login")
    public ResponseEntity<JSONObject> loginUser(@RequestBody User user) {
        return userService.loginUser(user);
    }

    @ApiOperation("Verify user token")
    @PostMapping("verify")
    public boolean verifyToken(@RequestBody String token) {
        return userService.verifyToken(token);
    }

    @ApiOperation("Check if user exists")
    @PostMapping("check")
    public User checkUser(@RequestBody User user) {
        return userService.userExists(user);
    }

    @ApiOperation("Find all users")
    @GetMapping("find/all")
    public List<User> findUsers() {
        return userService.findUsers();
    }

    @ApiOperation("Find a user by ID")
    @GetMapping("find/byid")
    public User findByID(@RequestParam("id") Long id) {
        return userService.findUserByID(id);
    }

    @ApiOperation("Find stats for all users")
    @GetMapping("stats")
    public List<UserQueryReturn> findUserStats() {
        return userService.findUserStats();
    }

    @ApiOperation("Delete user by email")
    @DeleteMapping("delete")
    public void deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
    }

}
