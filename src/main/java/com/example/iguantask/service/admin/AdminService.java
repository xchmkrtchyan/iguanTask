package com.example.iguantask.service.admin;

import com.example.iguantask.persistence.user.model.User;
import com.example.iguantask.rest.model.UserCloseRequest;
import com.example.iguantask.rest.model.UserRequest;
import com.example.iguantask.rest.model.UserUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    Boolean existsByUsername(String userRequestName);

    Boolean existsByEmail(String userRequestEmail);

    void createUser(UserRequest signUpRequest);

    void updateUser(UserUpdateRequest userUpdateRequest);

    void deleteUser(String username);

    List<User> findAll();

    Optional<User> findById(Long id);

    void closeUserAccount(String username);
}
