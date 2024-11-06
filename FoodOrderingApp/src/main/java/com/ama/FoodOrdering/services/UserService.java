package com.ama.FoodOrdering.services;
import com.ama.FoodOrdering.entities.User;
import com.ama.FoodOrdering.responses.UserResponse;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Map;

public interface UserService {
    User createUser(User user);

    User updateUser(Map<String, Object> updates) throws ChangeSetPersister.NotFoundException;

    void deleteUser() throws ChangeSetPersister.NotFoundException;

    UserResponse getUserById() throws ChangeSetPersister.NotFoundException;

    List<UserResponse> getAllUsers();

    User findByName(String name);


}

