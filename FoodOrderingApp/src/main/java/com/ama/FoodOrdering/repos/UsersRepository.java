package com.ama.FoodOrdering.repos;

import com.ama.FoodOrdering.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {

}
