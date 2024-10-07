package com.ama.FoodOrdering.services;

import com.ama.FoodOrdering.entities.MenuItem;
import org.springframework.data.crossstore.ChangeSetPersister;
import java.util.List;

public interface MenuService {
    public MenuItem addMenuItem(MenuItem menuItem, Long admin_id) throws ChangeSetPersister.NotFoundException;

    public void deleteMenuItem(Long menu_id, Long admin_id) throws ChangeSetPersister.NotFoundException;

    public List<MenuItem> addMenu(List<MenuItem> menuItems, Long admin_id) throws ChangeSetPersister.NotFoundException;

    public List<MenuItem> showDailyMenu();

    public MenuItem getDailySuggestion();
}
