package com.ama.FoodOrdering.services;
import com.ama.FoodOrdering.entities.Users;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Map;

public interface UserService {
    Users createUser(Users user);

    Users updateUser(Long user_id, Map<String, Object> updates) throws ChangeSetPersister.NotFoundException;

    void deleteUser(Long user_id) throws ChangeSetPersister.NotFoundException;

    Users getUserById(Long user_id) throws ChangeSetPersister.NotFoundException;

    List<Users> getAllUsers();

}

