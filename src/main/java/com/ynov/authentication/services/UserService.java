package com.ynov.authentication.services;

import com.ynov.authentication.models.User;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface UserService {
    User registerStudent(User student);

    Map<String, Object> login(String email, String password) throws NoSuchAlgorithmException;
}
