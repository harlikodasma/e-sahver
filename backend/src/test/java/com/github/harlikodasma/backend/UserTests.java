package com.github.harlikodasma.backend;

import com.github.harlikodasma.backend.bean.UserQueryReturn;
import com.github.harlikodasma.backend.controller.UserController;
import com.github.harlikodasma.backend.model.User;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class UserTests {

    @Autowired
    UserController userController;

    @Test
    void registerUser() {
        User newUser = new User(70L, "test@test.com", "test12", true, true);
        ResponseEntity<String> response = userController.registerUser(newUser);
        ResponseEntity<String> expectedResponse = new ResponseEntity<>(HttpStatus.OK);
        Assertions.assertSame(response.getStatusCode(), expectedResponse.getStatusCode());
        userController.deleteUser(Long.parseLong(response.getBody()));
    }

    @Test
    void checkUserExistence() {
        User checkedUser = new User(null, "admin@test.com", null, true, true);
        User response = userController.checkUser(checkedUser);
        Assertions.assertEquals(checkedUser.getEmail(), response.getEmail());
    }

    @Test
    void loginUser() {
        User loggedInUser = new User(70L, "admin@test.com", "test12", true, true);
        ResponseEntity<JSONObject> response = userController.loginUser(loggedInUser);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotEquals(response.getBody(), new JSONObject());
    }

    @Test
    void verifyUserToken() {
        Assertions.assertFalse(userController.verifyToken("eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NDQ5NjQ1MDcsInN1YiI6InRlc3RAdGVzdC5jb20iLCJleHAiOjE2NDUwNTA5MDd9.GDehxM45mBrxD4x4hgkfOH6Ej8HNnUqW6fBx41zVFyw"));
    }

    @Test
    void getUserStats() {
        List<UserQueryReturn> response = userController.findUserStats();
        List<UserQueryReturn> emptyResponse = new ArrayList<>();
        Assertions.assertNotEquals(response, emptyResponse);
    }

    @Test
    void findUser() {
        User fetchedUser = userController.findByID(1L);
        Assertions.assertEquals(fetchedUser.getEmail(), "admin@test.com");
        Assertions.assertTrue(fetchedUser.isAdmin());
        Assertions.assertFalse(fetchedUser.isBusinessClientAccount());
    }

    @Test
    void findAllUsers() {
        List<User> response = userController.findUsers();
        List<User> emptyResponse = new ArrayList<>();
        Assertions.assertNotEquals(response, emptyResponse);
    }
}
