package org.example.backendapi.entity;

import lombok.Getter;

// actual user in our database system.
@Getter
public class User {
    private String username;
    private String password;
    // any filed such as id, phone, email etc.
}
