package com.ama.FoodOrdering.services;
import com.ama.FoodOrdering.entities.User;
import com.ama.FoodOrdering.dto.UserResponse;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User updateUser(User updatedUser) throws ChangeSetPersister.NotFoundException;

    void deleteUser() throws ChangeSetPersister.NotFoundException;

    UserResponse getUserById() throws ChangeSetPersister.NotFoundException;

    List<UserResponse> getAllUsers();

    User findByName(String name);


}

