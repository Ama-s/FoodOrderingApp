package com.ama.FoodOrdering.serviceImplementations;

import com.ama.FoodOrdering.entities.Users;
import com.ama.FoodOrdering.repos.UsersRepository;
import com.ama.FoodOrdering.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Users createUser(Users user) {
        user.setCreatedOn(LocalDateTime.now());
        return usersRepository.save(user);
    }

    @Override
    public Users updateUser(Long user_id, Map<String, Object> updates) throws ChangeSetPersister.NotFoundException {
        Users user = usersRepository.findById(user_id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Users.class, key);
            if (field != null) {
                field.setAccessible(true);

                ReflectionUtils.setField(field, user, value);
            }
        });

        user.setModifiedOn(LocalDateTime.now());
        user.setModifiedBy(user_id);

        return usersRepository.save(user);
    }

    @Override
    public void deleteUser(Long user_id) throws ChangeSetPersister.NotFoundException {
        Users user = usersRepository.findById(user_id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        user.setDeletedOn(LocalDateTime.now());
        user.setDeletedBy(user_id);

        usersRepository.save(user);
    }

    @Override
    public Users getUserById(Long user_id) throws ChangeSetPersister.NotFoundException {
        return usersRepository.findById(user_id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }

    @Override
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }
}
