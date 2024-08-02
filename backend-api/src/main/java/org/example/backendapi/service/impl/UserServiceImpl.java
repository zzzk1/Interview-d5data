package org.example.backendapi.service.impl;

import org.example.backendapi.entity.request.UserRequest;
import org.example.backendapi.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public boolean login(UserRequest userRequest) {
        if (Objects.isNull(userRequest)) {
            return false;
        }

        if (Objects.isNull(userRequest.getUsername()) || Objects.isNull(userRequest.getPassword())) {
            return false;
        }

        return userRequest.getUsername().equals("test") && userRequest.getPassword().equals("123456");
    }
}
