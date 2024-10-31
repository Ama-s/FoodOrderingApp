package com.ama.FoodOrdering.repos;

import com.ama.FoodOrdering.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
