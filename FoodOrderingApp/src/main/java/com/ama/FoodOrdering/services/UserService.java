package com.ama.FoodOrdering.services;
import com.ama.FoodOrdering.entities.User;
import com.ama.FoodOrdering.responses.UserResponse;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Map;

public interface UserService {
    User createUser(User user);

    User updateUser(Long user_id, Map<String, Object> updates) throws ChangeSetPersister.NotFoundException;

    void deleteUser(Long user_id) throws ChangeSetPersister.NotFoundException;

    UserResponse getUserById(Long user_id) throws ChangeSetPersister.NotFoundException;

    List<UserResponse> getAllUsers();

    User findByName(String name);

}

