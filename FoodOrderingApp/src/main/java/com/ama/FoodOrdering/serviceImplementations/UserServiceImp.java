package com.ama.FoodOrdering.serviceImplementations;

import com.ama.FoodOrdering.entities.User;
import com.ama.FoodOrdering.enums.UserRole;
import com.ama.FoodOrdering.exceptions.ResourceNotFoundException;
import com.ama.FoodOrdering.repos.UserRepository;
import com.ama.FoodOrdering.responses.UserResponse;
import com.ama.FoodOrdering.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        user.setCreatedOn(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long user_id, Map<String, Object> updates) throws ResourceNotFoundException {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + user_id + " not found."));
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(User.class, key);
            if (field != null) {
                field.setAccessible(true);

                // to check if the field is an enum,since user class has an enum now
                if(key.equals("role")) {
                    value = UserRole.valueOf(value.toString().toUpperCase());
                }
                    ReflectionUtils.setField(field, user, value);
            }
        });
        user.setModifiedOn(LocalDateTime.now());
        user.setModifiedBy(user_id);

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            // Throwing custom exception when the user is not found
            throw new ResourceNotFoundException("User with ID " + userId + " not found.");
        }

        // Soft delete logic: setting deletedOn timestamp
        User existingUser = user.get();
        existingUser.setDeletedOn(LocalDateTime.now());
        userRepository.save(existingUser);
    }

    @Override
    public UserResponse getUserById(Long user_id) throws ResourceNotFoundException {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + user_id + " not found."));
        UserResponse response = new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
        return response;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getRole()
                ))
                .collect(Collectors.toList());
    }
}
