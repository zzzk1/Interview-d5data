package org.example.backendapi.service;

import org.example.backendapi.entity.request.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    private UserRequest userRequest = new UserRequest();

    // actually username we excepted.
    private final String username = "test";
    // actually password we excepted.
    private final String password = "123456";

    @Test
    void testLoginFailed() {
        // Test if username, password is null.
        assertFalse("username or password should null here.", userService.login(userRequest));

        userRequest.setUsername("");
        userRequest.setPassword("");
        // Test if username, password is empty.
        assertFalse("username or password should empty here.", userService.login(userRequest));

        // Test if username or password incorrect.
        userRequest.setUsername("error");
        userRequest.setPassword(password);
        assertFalse("username or password not excepted.", userService.login(userRequest));
        userRequest.setUsername(username);
        userRequest.setPassword("error");
        assertFalse("username or password not excepted.", userService.login(userRequest));
    }

    @Test
    void testLoginSuccess() {
        // Test if username, password correct.
        userRequest.setUsername(username);
        userRequest.setPassword(password);
        assertTrue("username or password not excepted.", userService.login(userRequest));
    }
}
