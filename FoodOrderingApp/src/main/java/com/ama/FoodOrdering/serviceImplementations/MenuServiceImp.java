package com.ama.FoodOrdering.serviceImplementations;

import com.ama.FoodOrdering.entities.MenuItem;
import com.ama.FoodOrdering.entities.User;
import com.ama.FoodOrdering.enums.UserRole;
import com.ama.FoodOrdering.repos.MenuItemRepository;
import com.ama.FoodOrdering.repos.UserRepository;
import com.ama.FoodOrdering.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MenuServiceImp implements MenuService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public MenuItem addMenuItem(MenuItem menuItem, Long admin_id) throws ChangeSetPersister.NotFoundException, AccessDeniedException {
        User user = userRepository.findById(admin_id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        if (user.getRole() != UserRole.ADMIN) {
            throw new AccessDeniedException("User is not authorized to perform this action");
        }
        menuItem.setCreatedBy(admin_id);
        return menuItemRepository.save(menuItem);
    }

    @Override
    public void deleteMenuItem(Long id, Long admin_id) throws ChangeSetPersister.NotFoundException, AccessDeniedException {
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        menuItem.setDeletedOn(LocalDateTime.now());

        User user = userRepository.findById(admin_id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        if (user.getRole() != UserRole.ADMIN) {
            throw new AccessDeniedException("User is not authorized to perform this action");
        }
        menuItem.setDeletedBy(admin_id);
        menuItemRepository.save(menuItem);
    }


    @Override
    public List<MenuItem> addMenu(List<MenuItem> menuItems, Long admin_id) throws ChangeSetPersister.NotFoundException, AccessDeniedException {

        User user = userRepository.findById(admin_id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        if (user.getRole() != UserRole.ADMIN) {
            throw new AccessDeniedException("User is not authorized to perform this action");
        }
        for (MenuItem menuItem : menuItems) {
            menuItem.setCreatedBy(admin_id);
        }
        return menuItemRepository.saveAll(menuItems);
    }

    @Override
    public MenuItem updateMenuItem(Long menu_id, Long admin_id, Map<String, Object> updates) throws ChangeSetPersister.NotFoundException, AccessDeniedException {
        User user = userRepository.findById(admin_id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        if (user.getRole() != UserRole.ADMIN) {

            throw new AccessDeniedException("User is not authorized to perform this action");
        }

        MenuItem menuItem = menuItemRepository.findById(menu_id).orElseThrow();

        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(MenuItem.class, key);
            if (field != null) {
                field.setAccessible(true);

                // Check if the field is an enum
                if (field.getType().isEnum()) {
                    Object enumValue = Enum.valueOf((Class<Enum>) field.getType(), value.toString());
                    ReflectionUtils.setField(field, menuItem, enumValue);
                } else {
                    ReflectionUtils.setField(field, menuItem, value);
                }
            }
        });
        menuItem.setModifiedBy(admin_id);
        return menuItemRepository.save(menuItem);
    }

    @Override
    public List<MenuItem> showDailyMenu(Long user_id) {
        // using the repository's findAll method for showDailyMenu
        return menuItemRepository.findByDeletedOnIsNull();

    }

    @Override
    public MenuItem getDailySuggestion(Long user_id) {
        // using the MenuItemRepository's custom query I created to get a random MenuItem
        Optional<MenuItem> randomMenuItem = menuItemRepository.findRandomMenuItem();
        return randomMenuItem.orElse(null);
    }
}
