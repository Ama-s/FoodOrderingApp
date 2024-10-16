package com.ama.FoodOrdering.controller;

import com.ama.FoodOrdering.entities.Users;
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
    public ResponseEntity<Users> createUser(@RequestBody Users user) throws ChangeSetPersister.NotFoundException {
        Users newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PatchMapping("/update/{user_id}")
    public ResponseEntity<Users> updateUser(@RequestBody Map<String, Object> updates,
                                            @PathVariable Long user_id){
        try{
            Users updatedUser = userService.updateUser(user_id, updates);
            return ResponseEntity.ok(updatedUser);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{user_id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long user_id) throws ChangeSetPersister.NotFoundException {
        userService.deleteUser(user_id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getUser/{user_id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long user_id) throws ChangeSetPersister.NotFoundException {
        Users user = userService.getUserById(user_id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

}

