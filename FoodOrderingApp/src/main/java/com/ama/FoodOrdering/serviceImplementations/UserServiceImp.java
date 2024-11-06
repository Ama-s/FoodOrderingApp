package com.ama.FoodOrdering.serviceImplementations;

import com.ama.FoodOrdering.entities.User;
import com.ama.FoodOrdering.enums.UserRole;
import com.ama.FoodOrdering.exceptions.ResourceNotFoundException;
import com.ama.FoodOrdering.repos.UserRepository;
import com.ama.FoodOrdering.responses.UserResponse;
import com.ama.FoodOrdering.services.AuthService;
import com.ama.FoodOrdering.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Override
    public User createUser(User user) {
        user.setCreatedOn(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Map<String, Object> updates) throws ResourceNotFoundException {
        User user = userRepository.findById(authService.getCurrentUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + authService.getCurrentUserId() + " not found."));
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
        user.setModifiedBy(authService.getCurrentUserId());

        return userRepository.save(user);
    }

    @Override
    public void deleteUser() {
        Optional<User> user = userRepository.findById(authService.getCurrentUserId());
        if (user.isEmpty()) {
            // Throwing custom exception when the user is not found
            throw new ResourceNotFoundException("User with ID " + authService.getCurrentUserId() + " not found.");
        }

        // Soft delete logic: setting deletedOn timestamp
        User existingUser = user.get();
        existingUser.setDeletedOn(LocalDateTime.now());
        userRepository.save(existingUser);
    }

    @Override
    public UserResponse getUserById() throws ResourceNotFoundException {
        User user = userRepository.findById(authService.getCurrentUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + authService.getCurrentUserId() + " not found."));
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
        User authorisedUuser = userRepository.findById(authService.getCurrentUserId()).orElseThrow();
        if (authorisedUuser.getRole() != UserRole.ADMIN) {
            try {
                throw new AccessDeniedException("User is not authorized to perform this action");
            } catch (AccessDeniedException e) {
                throw new RuntimeException(e);
            }
        }


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

    public User findByName(String name) {
        // Implement this in your service to find a user by `name`
        return userRepository.findByName(name);  // Assuming your repository has this method
    }

}
