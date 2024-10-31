package com.ama.FoodOrdering.controller;

import com.ama.FoodOrdering.entities.User;
import com.ama.FoodOrdering.exceptions.ResourceNotFoundException;
import com.ama.FoodOrdering.responses.UserResponse;
import com.ama.FoodOrdering.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PatchMapping("/update/{user_id}")
    public ResponseEntity<UserResponse> updateUser(@RequestBody Map<String, Object> updates,
                                                   @PathVariable Long user_id){
        try{
            User updatedUser = userService.updateUser(user_id, updates);
            UserResponse response = new UserResponse(
                    updatedUser.getId(),
                    updatedUser.getName(),
                    updatedUser.getEmail(),
                    updatedUser.getPassword(),
                    updatedUser.getRole()
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{user_id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long user_id) {
        try {
            userService.deleteUser(user_id);
            return ResponseEntity.noContent().build(); // 204 No Content when deletion is successful
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()); // Return custom error message
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong"); // Generic error handling
        }
    }


    @GetMapping("/getUser/{user_id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long user_id) throws ChangeSetPersister.NotFoundException {
        UserResponse userResponse = userService.getUserById(user_id);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }

}

