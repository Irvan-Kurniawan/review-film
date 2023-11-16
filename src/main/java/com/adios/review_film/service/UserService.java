package com.adios.review_film.service;

import com.adios.review_film.dto.request.NewUserRequest;
import com.adios.review_film.dto.request.UpdateUserRequest;
import com.adios.review_film.dto.response.UserResponse;
import com.adios.review_film.entity.User;

import java.util.List;


public interface UserService {
    UserResponse createUser(NewUserRequest newUserRequest);
    void deleteUser(String id);
    UserResponse updateUser(UpdateUserRequest updateUserRequest);
    List<UserResponse> findUserAll();
    User findUserById(String id);
    List<User> findUserByName(String name);
    List<UserResponse> findUserByNameResponse(String name);
    UserResponse findUserByIdResponse(String id);

}
