package org.example.backendapi.entity.request;

import lombok.Getter;
import lombok.Setter;

/**
 * The parameters passed by the front end need to be serialized.
 */
@Getter
@Setter
public class UserRequest {
    private String username;
    private String password;
}
