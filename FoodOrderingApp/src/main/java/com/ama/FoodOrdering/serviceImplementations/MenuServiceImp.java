package com.ama.FoodOrdering.serviceImplementations;

import com.ama.FoodOrdering.entities.MenuItem;
import com.ama.FoodOrdering.entities.Users;
import com.ama.FoodOrdering.repos.MenuItemRepository;
import com.ama.FoodOrdering.repos.UsersRepository;
import com.ama.FoodOrdering.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MenuServiceImp implements MenuService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public MenuItem addMenuItem(MenuItem menuItem, Long admin_id) throws ChangeSetPersister.NotFoundException {
        Users user = usersRepository.findById(admin_id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        menuItem.setCreatedBy(admin_id);
        return menuItemRepository.save(menuItem);
    }

    @Override
    public void deleteMenuItem(Long id, Long admin_id) throws ChangeSetPersister.NotFoundException {
        // is there any need to keep track of deletedOn and deletedBy info if deleting totally removes that instance from the db?
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        menuItem.setDeletedOn(LocalDateTime.now());

        Users user = usersRepository.findById(admin_id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        menuItem.setDeletedBy(admin_id);
        menuItemRepository.deleteById(id); // This will permanently remove the menu item
    }


    @Override
    public List<MenuItem> addMenu(List<MenuItem> menuItems, Long admin_id) throws ChangeSetPersister.NotFoundException {

        Users user = usersRepository.findById(admin_id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        for (MenuItem menuItem : menuItems) {
            menuItem.setCreatedBy(admin_id);
        }
        return menuItemRepository.saveAll(menuItems);
    }

    @Override
    public List<MenuItem> showDailyMenu() {
        // using the repository's findAll method for showDailyMenu
        return menuItemRepository.findAll();
    }

    @Override
    public MenuItem getDailySuggestion() {
        // using the MenuItemRepository's custom query I created to get a random MenuItem
        Optional<MenuItem> randomMenuItem = menuItemRepository.findRandomMenuItem();
        return randomMenuItem.orElse(null);
    }
}
