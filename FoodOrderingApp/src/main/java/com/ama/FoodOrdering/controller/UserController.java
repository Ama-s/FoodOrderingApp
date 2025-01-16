package com.ama.FoodOrdering.controller;

import com.ama.FoodOrdering.entities.User;
import com.ama.FoodOrdering.exceptions.ResourceNotFoundException;
import com.ama.FoodOrdering.dto.UserResponse;
import com.ama.FoodOrdering.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")

public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user) throws ChangeSetPersister.NotFoundException {
        User newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/updateUserDetails")
    public ResponseEntity<UserResponse> updateUser(@RequestBody User updatedUser) {
        try {
            User updatedUserEntity = userService.updateUser(updatedUser);
            UserResponse response = new UserResponse(
                    updatedUserEntity.getId(),
                    updatedUserEntity.getName(),
                    updatedUserEntity.getEmail(),
                    updatedUserEntity.getPassword(),
                    updatedUserEntity.getRole()
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser() {
        try {
            userService.deleteUser();
            return ResponseEntity.noContent().build(); // 204 No Content when deletion is successful
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()); // Return custom error message
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong"); // Generic error handling
        }
    }


    @GetMapping("/getMyDetails")
    public ResponseEntity<UserResponse> getUserById() throws ChangeSetPersister.NotFoundException {
        UserResponse userResponse = userService.getUserById();
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }

}

