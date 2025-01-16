package com.ama.FoodOrdering.repos;

import com.ama.FoodOrdering.entities.MenuItem;
import com.ama.FoodOrdering.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);

    List<User> findByDeletedOnIsNull();
}
