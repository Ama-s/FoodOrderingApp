package com.ama.FoodOrdering.services;

import com.ama.FoodOrdering.entities.MenuItem;
import org.springframework.data.crossstore.ChangeSetPersister;
import java.util.List;

public interface MenuService {
    public MenuItem addMenuItem(MenuItem menuItem);

    public void deleteMenuItem(String menu_id);

    public List<MenuItem> addMenu(List<MenuItem> menuItems);

    public List<MenuItem> showDailyMenu();

    public MenuItem getDailySuggestion();
}
