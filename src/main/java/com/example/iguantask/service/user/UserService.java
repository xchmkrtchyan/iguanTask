package com.example.iguantask.service.user;


import com.example.iguantask.rest.model.UserCloseRequest;
import com.example.iguantask.rest.model.UserRequest;
import com.example.iguantask.rest.model.UserUpdateRequest;


public interface UserService {

    Boolean existsByUsername(String userRequestName);

    Boolean existsByEmail(String userRequestEmail);

    void createUser(UserRequest signUpRequest);

    void updateUser(UserUpdateRequest userUpdateRequest);

    void closeUserAccount(UserCloseRequest userCloseRequest);
}
