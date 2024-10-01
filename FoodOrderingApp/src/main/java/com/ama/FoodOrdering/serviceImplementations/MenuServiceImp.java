package com.ama.FoodOrdering.serviceImplementations;

import com.ama.FoodOrdering.entities.MenuItem;
import com.ama.FoodOrdering.repos.MenuItemRepository;
import com.ama.FoodOrdering.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MenuServiceImp implements MenuService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Override
    public MenuItem addMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    @Override
    public void deleteMenuItem(String menu_id) throws ChangeSetPersister.NotFoundException {
        if(menuItemRepository.findById(menu_id).isEmpty()){
            throw new ChangeSetPersister.NotFoundException();
        }
    }

    @Override
    public List<MenuItem> addMenu(List<MenuItem> menuItems) {
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
