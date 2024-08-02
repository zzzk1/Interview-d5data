package org.example.backendapi.service;

import org.example.backendapi.entity.request.UserRequest;

public interface UserService {
    /**
     *
     * @param userRequest Bring the data needed to log in.
     * @return true if successful, false if failed.
     */
    boolean login(UserRequest userRequest);
}
