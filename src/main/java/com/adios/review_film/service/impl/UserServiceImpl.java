package com.adios.review_film.service.impl;

import com.adios.review_film.dto.request.NewUserRequest;
import com.adios.review_film.dto.request.UpdateUserRequest;
import com.adios.review_film.dto.response.UserResponse;
import com.adios.review_film.entity.User;
import com.adios.review_film.repository.UserRepository;
import com.adios.review_film.service.UserService;
import com.adios.review_film.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse createUser(NewUserRequest newUserRequest) {
        validationUtil.validate(newUserRequest);
        try {
            User user = User.builder()
                    .name(newUserRequest.getName())
                    .build();
            userRepository.saveAndFlush(user);
            return getUserResponse(user);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exist");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUser(String id) {
        User orElseThrow = getOrElseThrow(id);
        userRepository.delete(orElseThrow);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse updateUser(UpdateUserRequest updateUserRequest) {
        validationUtil.validate(updateUserRequest);
        try {
            getOrElseThrow(updateUserRequest.getId());
            User user = User.builder()
                    .id(updateUserRequest.getId())
                    .name(updateUserRequest.getName())
                    .build();
            userRepository.update(user);
            return getUserResponse(user);
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Data already exists");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserResponse> findUserAll() {
        return userRepository.findAll().stream().map(this::getUserResponse).collect(Collectors.toList());
    }

    @Override
    public User findUserById(String id) {
        return getOrElseThrow(id);
    }

    @Override
    public List<User> findUserByName(String name) {
        return getOrElseThrowName(name);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserResponse> findUserByNameResponse(String name) {
        List<User> orElseThrow = getOrElseThrowName(name);
        return orElseThrow.stream().map(this::getUserResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse findUserByIdResponse(String id) {
        User orElseThrow = getOrElseThrow(id);
        return getUserResponse(orElseThrow);
    }
    @Transactional(readOnly = true)
    private UserResponse getUserResponse(User orElseThrow) {
        return UserResponse.builder()
                .id(orElseThrow.getId())
                .name(orElseThrow.getName())
                .build();
    }

    @Transactional(readOnly = true)
    private User getOrElseThrow(String id) {
        User byId = userRepository.findById(id);
        if(byId!=null){
            return byId;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with such ID does not exist");
    }
    @Transactional(readOnly = true)
    private List<User> getOrElseThrowName(String name) {
        return userRepository.findByName(name);
    }
}
