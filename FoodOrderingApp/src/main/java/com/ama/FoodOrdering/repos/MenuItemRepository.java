package com.ama.FoodOrdering.repos;

import com.ama.FoodOrdering.entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository <MenuItem, Long> {

    // a custom query is created to select a random menu item.
    @Query(value = "SELECT * FROM menu_item ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<MenuItem> findRandomMenuItem();

    // Method to delete a menu item by its id
    void deleteById(Long menu_id);

    // query to get daily menu
    // this will return all MenuItem objects that haven't been deleted yet from the database
    List<MenuItem> findByDeletedOnIsNull();
}
