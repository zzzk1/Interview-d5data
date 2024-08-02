package org.example.backendapi.controller;

import org.example.backendapi.common.RespStatusEnum;
import org.example.backendapi.common.Result;
import org.example.backendapi.entity.request.UserRequest;
import org.example.backendapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
    @Autowired
    private UserService userService;

    @GetMapping("/helloWorld")
    public Result helloWorld() {
        return Result.success("Hello World!");
    }

    @PostMapping("/login")
    public Result login(@RequestBody UserRequest userRequest) {
        boolean isLogined = userService.login(userRequest);
        if (isLogined) {
            return Result.success(RespStatusEnum.SUCCESSFUL, "login successful");
        }

        return Result.fail(RespStatusEnum.INCORRECT_USERNAME_OR_PASSWORD, "login failed, incorrect username or password");
    }
}
