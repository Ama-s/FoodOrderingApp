package com.ama.FoodOrdering.services;

import com.ama.FoodOrdering.entities.MenuItem;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;

public interface MenuService {
    public MenuItem addMenuItem(MenuItem menuItem, Long admin_id) throws ChangeSetPersister.NotFoundException, AccessDeniedException;

    public void deleteMenuItem(Long menu_id, Long admin_id) throws ChangeSetPersister.NotFoundException, AccessDeniedException;

    public List<MenuItem> addMenu(List<MenuItem> menuItems, Long admin_id) throws ChangeSetPersister.NotFoundException, AccessDeniedException;

    public MenuItem updateMenuItem(Long menu_id, Long admin_id, Map<String, Object> updates) throws ChangeSetPersister.NotFoundException, AccessDeniedException;

    public List<MenuItem> showDailyMenu(Long user_id);

    public MenuItem getDailySuggestion(Long user_id);
}
