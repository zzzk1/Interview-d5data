package org.example.backendapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backendapi.BackendApiApplication;
import org.example.backendapi.entity.request.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BackendApiApplication.class)
@AutoConfigureMockMvc
public class HelloControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final UserRequest userRequest = new UserRequest();

    @BeforeEach
    void setUp() {
        userRequest.setUsername("test");
        userRequest.setPassword("123456");
    }

    @Test
    void testHello() throws Exception {
        mockMvc.perform(get("/helloWorld"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"code\":200,\"message\":\"successful\",\"data\":\"Hello World!\"}"));
    }

    @Test
    void testLoginFailed() throws Exception {
        // test empty username and password.
        userRequest.setUsername("");
        userRequest.setPassword("");
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"code\":400100,\"message\":\"login failed, incorrect username or password\",\"data\":null}"));

        // test incorrect username.
        userRequest.setUsername("username");
        userRequest.setPassword("1213456");
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"code\":400100,\"message\":\"login failed, incorrect username or password\",\"data\":null}"));

        // test incorrect password.
        userRequest.setUsername("test");
        userRequest.setPassword("password");
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"code\":400100,\"message\":\"login failed, incorrect username or password\",\"data\":null}"));
    }

    @Test
    void testLoginSuccess() throws Exception {
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"code\":200,\"message\":\"login successful\",\"data\":null}"));
    }
}
