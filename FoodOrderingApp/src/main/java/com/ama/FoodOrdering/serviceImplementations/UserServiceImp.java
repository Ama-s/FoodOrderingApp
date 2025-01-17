package com.ama.FoodOrdering.serviceImplementations;

import com.ama.FoodOrdering.entities.User;
import com.ama.FoodOrdering.enums.UserRole;
import com.ama.FoodOrdering.exceptions.ResourceNotFoundException;
import com.ama.FoodOrdering.repos.UserRepository;
import com.ama.FoodOrdering.dto.UserResponse;
import com.ama.FoodOrdering.services.AuthService;
import com.ama.FoodOrdering.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
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
    public User updateUser(User updatedUser) throws ResourceNotFoundException {
        User existingUser = userRepository.findById(authService.getCurrentUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + authService.getCurrentUserId() + " not found."));

        // Update fields explicitly
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setRole(updatedUser.getRole());

        // Audit fields
        existingUser.setModifiedOn(LocalDateTime.now());
        existingUser.setModifiedBy(authService.getCurrentUserId());

        return userRepository.save(existingUser);
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


        List<User> users = userRepository.findByDeletedOnIsNull();

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
