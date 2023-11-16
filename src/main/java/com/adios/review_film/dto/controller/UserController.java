package com.adios.review_film.dto.controller;

import com.adios.review_film.dto.request.NewUserRequest;
import com.adios.review_film.dto.request.UpdateUserRequest;
import com.adios.review_film.dto.response.CommonResponse;
import com.adios.review_film.dto.response.UserResponse;
import com.adios.review_film.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/api/users")
    public ResponseEntity<?> createUser(@RequestBody NewUserRequest newUserRequest){
        UserResponse user = userService.createUser(newUserRequest);
        CommonResponse commonResponse = CommonResponse.builder()
                .message("Create new User successful")
                .statusCode(HttpStatus.CREATED.value())
                .data(user)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }
    @GetMapping(value = "/api/users/{id}")
    public ResponseEntity<?> findUserByID(@PathVariable String id){
        UserResponse userByIdResponse = userService.findUserByIdResponse(id);
        CommonResponse commonResponse = CommonResponse.builder()
                .message("Find User by ID successful")
                .statusCode(HttpStatus.OK.value())
                .data(userByIdResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
    @GetMapping(value = "/api/users/search")
    public ResponseEntity<?> findUserByName(@RequestParam String name) {
        List<UserResponse> userByIdResponse = userService.findUserByNameResponse(name);
        CommonResponse commonResponse = CommonResponse.builder()
                .message("Find User by Name successful")
                .statusCode(HttpStatus.OK.value())
                .data(userByIdResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
    @GetMapping(value = "/api/users")
    public ResponseEntity<?> findUserAll(){
            List<UserResponse> userServiceAll = userService.findUserAll();

            CommonResponse<List<UserResponse>> commonResponse = CommonResponse.<List<UserResponse>>builder()
                    .message("All user get")
                    .statusCode(HttpStatus.OK.value())
                    .data(userServiceAll)
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
    @PutMapping(value = "/api/users")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest updateUserRequest){
        UserResponse userResponse = userService.updateUser(updateUserRequest);
        CommonResponse commonResponse = CommonResponse.builder()
                .message("Update User Successful")
                .statusCode(HttpStatus.OK.value())
                .data(userResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
    @DeleteMapping(value = "/api/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        CommonResponse commonResponse = CommonResponse.builder()
                .message("Delete User successful")
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
